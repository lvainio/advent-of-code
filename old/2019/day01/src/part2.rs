use std::fs;

fn calc_fuel(mut number: i64) -> i64 {
    let mut fuel_sum = 0;
    while number > 0 {
        number = number / 3 - 2;
        if number > 0 {
            fuel_sum += number;
        }
    }
    fuel_sum
}

pub fn part2() {
    let content = match fs::read_to_string("input.txt") {
        Ok(c) => c,
        Err(error) => panic!("Problem opening the file: {error:?}")
    };    

    let lines = content.lines();
    let mut fuel_sum: i64 = 0;

    for line in lines {
        match line.parse::<i64>() {
            Ok(number) => fuel_sum += calc_fuel(number),
            Err(error) => panic!("Failed to parse the number: {error:?}")
        }
    }

    println!("part2: {fuel_sum}");
}