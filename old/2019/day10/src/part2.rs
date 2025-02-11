use std::{cmp::Ordering, collections::VecDeque, fs};

struct Vector {
    dr: i64,
    dc: i64,
}

impl Vector {
    fn new(start: (i64, i64), end: (i64, i64)) -> Self {
        Vector {
            dr: end.0 - start.0,
            dc: end.1 - start.1,
        }
    }

    fn angle(&self) -> f64 {
        std::f64::consts::PI - (self.dc as f64).atan2(self.dr as f64)
    }

    fn length(&self) -> f64 {
        ((self.dr.pow(2) + self.dc.pow(2)) as f64).sqrt()
    }
}

impl PartialOrd for Vector {
    fn partial_cmp(&self, other: &Self) -> Option<Ordering> {
        Some(self.cmp(other))
    }
}

impl Ord for Vector {
    fn cmp(&self, other: &Self) -> Ordering {
        let angle_ord = self.angle().partial_cmp(&other.angle()).unwrap();
        if angle_ord == Ordering::Equal {
            self.length().partial_cmp(&other.length()).unwrap()
        } else {
            angle_ord
        }
    }
}

impl PartialEq for Vector {
    fn eq(&self, other: &Self) -> bool {
        self.angle() == other.angle() && self.length() == other.length()
    }
}

impl Eq for Vector {}

pub fn solve() {
    let input = fs::read_to_string("input.txt").unwrap();
    let input: Vec<Vec<char>> = input.lines().map(|line| line.chars().collect()).collect();

    let station_r = 29;
    let station_c = 26;

    let mut vecs: Vec<Vector> = Vec::new();
    for r in 0..input.len() {
        for c in 0..input[0].len() {
            if input[r][c] == '#' && !(r == station_r && c == station_c) {
                vecs.push(Vector::new(
                    (station_r as i64, station_c as i64),
                    (r as i64, c as i64),
                ));
            }
        }
    }
    vecs.sort();

    let mut queue = VecDeque::from(vecs);
    let mut current_angle = f64::MIN;
    let mut counter = 1;
    while let Some(v) = queue.pop_front() {
        if counter == 200 {
            println!("Part2: ");
            println!(
                "({}, {})",
                (station_r as i64) + v.dr,
                (station_c as i64) + v.dc
            );
            println!(
                "{}",
                ((station_c as i64) + v.dc) * 100 + (station_r as i64) + (v.dr)
            );
            break;
        }
        if v.angle() == current_angle {
            queue.push_back(v);
        } else {
            counter += 1;
            current_angle = v.angle();
        }
    }
}
