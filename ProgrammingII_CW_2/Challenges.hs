import Data.Char
import Control.Applicative
import Parsing

data Expr = App Expr Expr | Lam Int Expr | Var Int  deriving (Show,Eq)


-------------------------------------------
-- Iason Koulas ##### github.com/jakougr --
-------------------------------------------

----------Utilities-----------
--Implementing some functions from Data.List
nub                     :: (Eq a) => [a] -> [a]
nub                      = nubBy (==)

nubBy                   :: (a -> a -> Bool) -> [a] -> [a]
nubBy eq []              = []
nubBy eq (x:xs)          = x : nubBy eq (filter (\y -> not (eq x y)) xs)

union xs ys = xs ++ filter (`notElem` xs) (nub ys)

delete' :: (Eq a) => a -> [a] -> [a]
delete' y [] = []
delete' y (x:xs) = if x == y then xs else x : delete' y xs

--1a
-- This function is breaking the expression App->Lam->Var in order to find the free variables.
freeVariables :: Expr ->[Int]
freeVariables (Var i) = [i]
freeVariables (App a b) = union (freeVariables a) (freeVariables b)
freeVariables (Lam i e) = delete' i $ freeVariables e

--1b
rename :: Expr -> Int -> Int -> Expr

rename (Var j) y e = if j == y then (Var e) else (Var j)
rename (Lam i e1) x v --This if solves any issues with nesting. Like not renaming any Var with the same number in another Lam
    | i == x && (not (elem i (freeVariables e1))) = Lam v e1
    | i == x && (elem i (freeVariables e1)) = Lam v (rename e1 x v)
    | otherwise = Lam i (rename e1 x v)
rename (App e1 e2) x v = App (rename e1 x v) (rename e2 x v) --recursion for each variable
 --1c
--Pretty straightforward. Again breaking down more complex concepts to Var

alphaEquivalent :: Expr -> Expr -> Bool
alphaEquivalent (Var x) (Var y) = x == y
alphaEquivalent (Lam i e1) (Lam k e2) = (Lam i e1) == (rename (Lam k e2) k i) --This A-equivalence
alphaEquivalent (App e1 e2) (App e3 e4) = (alphaEquivalent e1 e3) && (alphaEquivalent e2 e4)
alphaEquivalent _ _ = False -- eg Var 1 = Lam 1 (Var 2)

--1d
--This function basically checks if beta-reduction is possible
hasRedex :: Expr -> Bool
hasRedex e = not ( reduce e == e )

reduce :: Expr -> Expr
reduce (Var v)     = Var v
reduce (Lam a t)   = Lam a (reduce t)
reduce (App t1 t2) = App (reduce t1) (reduce t2)
--1e
--Subbing an expr with another expr
substitute :: Expr -> Int -> Expr -> Expr
substitute (Lam y body) x newVal --i f equal sub it else continue searching
  | x /= y     = Lam y (substitute body x newVal )
  | otherwise = Lam y body
substitute (App e1 e2) x newVal  = -- break it down and search for the expr
  App (substitute e1 x newVal ) (substitute e1 x newVal )
substitute (Var y) x newVal -- if equal sub it else continue searching
  | x == y     = newVal
  | otherwise = Var y

prettyPrint :: Expr -> String
-- "/" is my lambda
--Had an issue with the function printing lambdas for any Lam but fixed it
prettyPrint (Var v)  =  "x" ++ show(v) -- "x" + number
--This is how I solved the many lambdas issue.
prettyPrint (Lam i (Lam j t2)) = "/x" ++ show (i) ++ "x" ++ show (j)  ++ (prettyPrint t2)
prettyPrint (Lam n t) = "/x" ++ show (n) ++ (prettyPrint t)
prettyPrint (App t1 t2) = "->" ++ (prettyPrint t1) ++ (prettyPrint t2) -- Break it and analyze it

--Translator
--I just translated to code the expressions from the specs + the new data type
--It's partially wrong as I couldn't implement a newtype for the partially translated
--part below . I am also checking with "free" as instructed.
data ExprCL = AppCL ExprCL ExprCL | Almost Expr ExprCL | K | S | VarCL Int deriving (Show)
newtype Almost = Expr (Expr , ExprCL)

translate :: Expr -> ExprCL
translate (Var x) = VarCL x
translate (App e1 e2) = AppCL (translate e1) (translate e2)
translate (Lam x e) | free x e == False = AppCL (K) (translate e)
translate (Lam x (Var y)) | x == y = AppCL (AppCL (S) (K) ) (K)
--translate (Lam x (Lam y e))
  --  | (free x e) = translate (Lam x (translate (Lam y e)))
    --   I cannot implement the new type in order for it to compile
translate (Lam x (App e1 e2))
    | (free x e1 || free x e2) = AppCL (S) (AppCL (translate (Lam x e1)) (translate (Lam x e2)))

free :: Int -> Expr -> Bool
free a (Var y) = a == y
free a (Lam y e)
            | a /= y = free a e
            | a == y = False
free a (App e1 e2) = (free a e1) || (free a e2)
free _ _ = False
