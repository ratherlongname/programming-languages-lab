import Data.List

-------------------------------------------------
-- Check whether set is empty
is_set_empty :: [Int] -> Bool
is_set_empty [] = True
is_set_empty _ = False

-- Tests: is_set_empty
test_01 = is_set_empty []
test_02 = is_set_empty [10]
test_03 = is_set_empty [1, 2, 3]

-------------------------------------------------
-- Union of two sets
union_of_sets :: [Int] -> [Int] -> [Int]
union_of_sets [] [] = []
union_of_sets [] x = nub x
union_of_sets x [] = nub x
union_of_sets x y = nub (x ++ y)    -- nub returns list
                                    -- with distinct
                                    -- elements only

-- Tests: union_of_sets
test_04 = union_of_sets [] []
test_05 = union_of_sets [] [1]
test_06 = union_of_sets [2, 3] []
test_07 = union_of_sets [1, 2, 3] [1, 2, 3]
test_08 = union_of_sets [1, 2, 3, 4] [3, 4, 5, 6, 7, 8, 9]

-------------------------------------------------
-- Intersection of two sets
intersect_sets :: [Int] -> [Int] -> [Int]
intersect_sets [] [] = []
intersect_sets [] _ = []
intersect_sets _ [] = []
intersect_sets x y = [e | e <- (nub x), elem e (nub y)]

-- Tests: intersect_sets
test_09 = intersect_sets [] []
test_10 = intersect_sets [] [1]
test_11 = intersect_sets [2, 3] []
test_12 = intersect_sets [1, 2, 3] [1, 2, 3]
test_13 = intersect_sets [1, 2, 3, 4] [3, 4, 5, 6, 7, 8, 9]

-------------------------------------------------
-- Subtract one set from another
-- (subtract_sets A B) means (A - B)
subtract_sets :: [Int] -> [Int] -> [Int]
subtract_sets [] [] = []
subtract_sets [] _ = []
subtract_sets x [] = x
subtract_sets x y = [e | e <- (nub x), notElem e y]

-- Tests: subtract_sets
test_14 = subtract_sets [] []
test_15 = subtract_sets [] [1]
test_16 = subtract_sets [2, 3] []
test_17 = subtract_sets [1, 2, 3] [1, 2, 3]
test_18 = subtract_sets [1, 2, 3, 4] [3, 4, 5, 6, 7, 8, 9]

-------------------------------------------------
-- Addition of two sets
add_sets :: [Int] -> [Int] -> [Int]
add_sets [] [] = []
add_sets [] x = nub x
add_sets x [] = nub x
add_sets x y = [ex + ey | ex <- (nub x), ey <- (nub y)]

-- Tests: add_sets
test_19 = add_sets [] []
test_20 = add_sets [] [1]
test_21 = add_sets [2, 3] []
test_22 = add_sets [1, 2, 3] [1, 2, 3]
test_23 = add_sets [1, 2, 3, 4] [3, 4, 5, 6, 7, 8, 9]

-------------------------------------------------
