fn parse_data() -> Vec<(char, i64)> {
    let mut lines = Vec::new();
    for line in std::fs::read_to_string("input.txt").unwrap().lines() {
        let character: char = line.chars().next().unwrap();
        let number: i64 = line[1..].parse().unwrap();
        lines.push((character, number))
    }
    return lines;
}


enum Direction {
    North,
    East,
    South,
    West,
}


impl Direction {
    fn from_degrees(degrees: i64) -> Option<Direction> {
        match degrees {
            0 => Some(Direction::North),
            90 => Some(Direction::East),
            180 => Some(Direction::South),
            270 => Some(Direction::West),
            _ => None
        }
    }
}


fn part1(lines: &Vec<(char, i64)>) -> i64 {
    let mut x: i64 = 0;
    let mut y: i64 = 0;
    let mut deg: i64 = 90; 

    for (character, number) in lines {
        if *character == 'N' {
            y -= number
        } else if *character == 'S' {
            y += number
        } else if *character == 'W' {
            x -= number
        } else if *character == 'E' {
            x += number
        } else if *character == 'L' {
            deg = ((deg - number) + 360) % 360
        } else if *character == 'R' {
            deg = (deg + number) % 360
        } else if *character == 'F' {
            let direction = Direction::from_degrees(deg);
            match direction {
                Some(Direction::North) => y -= number,
                Some(Direction::East) => x += number,
                Some(Direction::South) => y += number,
                Some(Direction::West) => x -= number,
                None => println!("Invalid degree"),
            }
        } 
    }
    return x.abs() + y.abs();
}


fn part2(lines: &Vec<(char, i64)>) -> i64 {
    let mut x_wp: i64 = 10;
    let mut y_wp: i64 = -1;
    let mut x_pos: i64 = 0;
    let mut y_pos: i64 = 0;

    for (character, number) in lines {
        if *character == 'N' {
            y_wp -= number
        } else if *character == 'S' {
            y_wp += number
        } else if *character == 'W' {
            x_wp -= number
        } else if *character == 'E' {
            x_wp += number
        } else if *character == 'L' {
            let temp_x: i64 = x_wp;
            let temp_y: i64 = y_wp;
            if *number == 90 {
                x_wp = temp_y;
                y_wp = -temp_x;
            } else if *number == 180 {
                x_wp = -temp_x;
                y_wp = -temp_y;
            } else if *number == 270 {
                x_wp = -temp_y;
                y_wp = temp_x;
            }     
        } else if *character == 'R' {
            let temp_x: i64 = x_wp;
            let temp_y: i64 = y_wp;
            if *number == 90 {
                x_wp = -temp_y;
                y_wp = temp_x;
            } else if *number == 180 {
                x_wp = -temp_x;
                y_wp = -temp_y;
            } else if *number == 270 {
                x_wp = temp_y;
                y_wp = -temp_x;
            }
        } else if *character == 'F' {
            x_pos += x_wp * number;
            y_pos += y_wp * number;
        } 
    }
    return x_pos.abs() + y_pos.abs();
}


fn main() {
    let lines = parse_data();

    let p1 = part1(&lines);
    let p2 = part2(&lines);
   
    println!("{} {}", p1, p2);
}