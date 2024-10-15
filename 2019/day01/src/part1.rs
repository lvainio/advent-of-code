use std::fs;

pub fn part1() {
    let content = match fs::read_to_string("input.txt") {
        Ok(c) => c,
        Err(error) => panic!("Problem opening the file: {error:?}")
    };    

    let lines = content.lines();
    let mut fuel_sum: i64 = 0;

    for line in lines {
        match line.parse::<i64>() {
            Ok(number) => fuel_sum += (number/3) - 2,
            Err(error) => panic!("Failed to parse the number: {error:?}")
        }
    }

    println!("part1: {fuel_sum}");
}