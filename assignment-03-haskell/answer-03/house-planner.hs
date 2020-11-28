possible_bed_sizes = reverse [[l, w] | l <- [10..15], w <- [10..15]]
possible_hall_sizes = reverse [[l, w] | l <- [15..20], w <- [10..15]]
possible_kitchen_sizes = reverse [[l, w] | l <- [7..15], w <- [5..13]]
possible_bath_sizes = reverse [[l, w] | l <- [4..8], w <- [5..9]]
possible_balcony_sizes = reverse [[l, w] | l <- [5..10], w <- [5..10]]
possible_garden_sizes = reverse [[l, w] | l <- [10..20], w <- [10..20]]

is_kitchen_size_valid :: [Int] -> [Int] -> [Int] -> Bool
is_kitchen_size_valid kitchen_size bed_size hall_size =
    (product kitchen_size) <= (product bed_size)
    &&
    (product kitchen_size) <= (product hall_size)

is_bath_size_valid :: [Int] -> [Int] -> Bool
is_bath_size_valid bath_size kitchen_size =
    (product bath_size) <= (product kitchen_size)

calc_space :: [Int]->[[Int]]->Int
calc_space room_counts room_dims = bed_space + hall_space + kitchen_space + bathrooms_space + garden_space + balcony_space
    where
        bed_space    = (room_counts!!0) * ((room_dims!!0)!!0) * ((room_dims!!0)!!1)
        hall_space       = (room_counts!!1) * ((room_dims!!1)!!0) * ((room_dims!!1)!!1)
        kitchen_space    = (room_counts!!2) * ((room_dims!!2)!!0) * ((room_dims!!2)!!1)
        bathrooms_space  = (room_counts!!3) * ((room_dims!!3)!!0) * ((room_dims!!3)!!1)
        garden_space     = (room_counts!!4) * ((room_dims!!4)!!0) * ((room_dims!!4)!!1)
        balcony_space    = (room_counts!!5) * ((room_dims!!5)!!0) * ((room_dims!!5)!!1)

plan ::Int->Int->Int->IO ()
plan total_space num_bed num_hall = do
    let num_kitchen = ceiling (fromIntegral num_bed / fromIntegral 3)
    let num_bath = num_bed + 1
    let num_balcony = 1
    let num_garden = 1

    let possible_dimensions = [[bed, hall, kitchen, bath, balcony, garden]
                            | bed <- possible_bed_sizes
                            , (product (num_bed:bed)) <= total_space
                            , hall <- possible_hall_sizes
                            , (sum ( map product [  num_bed:bed
                                                    , num_hall:hall
                                                 ]
                                    )
                                ) <= total_space
                            , kitchen <- possible_kitchen_sizes
                            , is_kitchen_size_valid kitchen bed hall
                            , (sum ( map product [  num_bed:bed
                                                    , num_hall:hall
                                                    , num_kitchen:kitchen
                                                 ]
                                    )
                                ) <= total_space
                            , bath <- possible_bath_sizes
                            , is_bath_size_valid bath kitchen
                            , (sum ( map product [  num_bed:bed
                                                    , num_hall:hall
                                                    , num_kitchen:kitchen
                                                    , num_bath:bath
                                                 ]
                                    )
                                ) <= total_space
                            , balcony <- possible_balcony_sizes
                            , (sum ( map product [  num_bed:bed
                                                    , num_hall:hall
                                                    , num_kitchen:kitchen
                                                    , num_bath:bath
                                                    , num_balcony:balcony
                                                 ]
                                    )
                                ) <= total_space
                            , garden <- possible_garden_sizes
                            , (sum ( map product [  num_bed:bed
                                                    , num_hall:hall
                                                    , num_kitchen:kitchen
                                                    , num_bath:bath
                                                    , num_balcony:balcony
                                                    , num_garden:garden
                                                 ]
                                    )
                                ) <= total_space
                          ]
    
    let room_counts = [num_bed, num_hall, num_kitchen, num_bath, num_balcony, num_garden]
    let result = take 1 possible_dimensions
    
    if null result then
        putStrLn "No design possible for the given constraints"
    else
        putStrLn (  "Rooms: [Bedroom, Hall, Kitchen, Bathroom, Balcony, Garden]"
                    ++ "\nCounts: " ++ (show room_counts)
                    ++ "\nDimensions: " ++ (show (result!!0))
                    ++ "\nUnused space: " ++ (show (total_space - (calc_space room_counts (result!!0))))
                 )