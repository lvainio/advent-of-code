use std::{fs, i64};

struct Point {
    x: i64,
    y: i64,
}

impl Point {
    fn manhattan_distance_to_origo(&self) -> i64 {
        self.x.abs() + self.y.abs()
    }
}

struct Line {
    p1: Point,
    p2: Point,
}

impl Line {
    fn is_horizontal(&self) -> bool {
        self.p1.y == self.p2.y
    }

    fn get_intersection(&self, other: &Line) -> Option<Point> {
        let left1 = self.p1.x.min(self.p2.x);
        let right1 = self.p1.x.max(self.p2.x);
        let left2 = other.p1.x.min(other.p2.x);
        let right2 = other.p1.x.max(other.p2.x);

        let x_overlap = left1 <= right2 && left2 <= right1;

        let bottom1 = self.p1.y.min(self.p2.y);
        let top1 = self.p1.y.max(self.p2.y);
        let bottom2 = other.p1.y.min(other.p2.y);
        let top2 = other.p1.y.max(other.p2.y);

        let y_overlap = bottom1 <= top2 && bottom2 <= top1;

        if x_overlap && y_overlap {
            let x_new;
            let y_new;
            if self.is_horizontal() {
                x_new = other.p1.x;
                y_new = self.p1.y;
            } else {
                x_new = self.p1.x;
                y_new = other.p1.y;
            }
            Some(Point { x: x_new, y: y_new })
        } else {
            None
        }
    }
}

fn parse_commands(commands: &str) -> Vec<Line> {
    let commands = commands.split(',');
    let mut position: (i64, i64) = (0, 0);
    let mut lines: Vec<Line> = vec![];

    for command in commands {
        let dir: char = command.chars().next().unwrap();
        let dist: i64 = command[1..].parse().unwrap();
        let position_copy = position.clone();
        match dir {
            'U' => position.1 += dist,
            'D' => position.1 -= dist,
            'L' => position.0 -= dist,
            'R' => position.0 += dist,
            _ => panic!("Invalid direction"),
        }
        let p1 = Point {
            x: position_copy.0,
            y: position_copy.1,
        };
        let p2 = Point {
            x: position.0,
            y: position.1,
        };
        lines.push(Line { p1, p2 });
    }
    lines
}

pub fn part1() {
    let content = fs::read_to_string("input.txt").unwrap();

    let input_lines: Vec<&str> = content.lines().collect();

    let lines1 = parse_commands(input_lines[0]);
    let lines2 = parse_commands(input_lines[1]);

    let mut closest = i64::MAX;
    for line1 in &lines1 {
        for line2 in &lines2 {
            match line1.get_intersection(&line2) {
                Some(point) => {
                    let dist = point.manhattan_distance_to_origo();
                    if dist != 0 && dist < closest {
                        closest = dist;
                    };
                }
                None => continue,
            }
        }
    }
    println!("Part1 {closest}");
}
