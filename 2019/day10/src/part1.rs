use std::collections::VecDeque;
use std::{fs, vec};

#[derive(Debug)]
enum Dir {
    North,
    NorthEast,
    East,
    SouthEast,
    South,
    SouthWest,
    West,
    NorthWest,
}

impl Dir {
    fn offsets(&self) -> (isize, isize) {
        match self {
            Dir::North => (-1, 0),
            Dir::NorthEast => (-1, 1),
            Dir::East => (0, 1),
            Dir::SouthEast => (1, 1),
            Dir::South => (1, 0),
            Dir::SouthWest => (1, -1),
            Dir::West => (0, -1),
            Dir::NorthWest => (-1, -1),
        }
    }
}

#[derive(Debug)]
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

    fn is_same_direction(&self, other: &Vector) -> bool {
        self.dc * other.dr == self.dr * other.dc
            && (self.dr.signum() == other.dr.signum())
            && (self.dc.signum() == other.dc.signum())
    }
}

pub fn solve() {
    let input = fs::read_to_string("input.txt").unwrap();
    let input: Vec<Vec<char>> = input.lines().map(|line| line.chars().collect()).collect();

    let directions = vec![
        Dir::North,
        Dir::NorthEast,
        Dir::East,
        Dir::SouthEast,
        Dir::South,
        Dir::SouthWest,
        Dir::West,
        Dir::NorthWest,
    ];

    let mut max_asteroids = i64::MIN;
    let mut best_row = 0;
    let mut best_col = 0;
    for r1 in 0..input.len() {
        for c1 in 0..input[0].len() {
            if input[r1][c1] == '.' {
                continue;
            }

            let mut visited = vec![vec![false; input[0].len()]; input.len()];
            let mut queue = VecDeque::new();
            let mut vectors: Vec<Vector> = Vec::new();

            queue.push_back((r1, c1));
            visited[r1][c1] = true;

            while let Some((r2, c2)) = queue.pop_front() {
                if !(r1 == r2 && c1 == c2) && input[r2][c2] == '#' {
                    let mut blocked = false;
                    let v_new = Vector::new((r1 as i64, c1 as i64), (r2 as i64, c2 as i64));
                    for vector in &vectors {
                        if vector.is_same_direction(&v_new) {
                            blocked = true;
                            break;
                        }
                    }
                    if !blocked {
                        vectors.push(v_new);
                    }
                }

                for dir in &directions {
                    let (dr, dc) = dir.offsets();

                    let new_r = c2 as isize + dr;
                    let new_c = r2 as isize + dc;

                    if new_r >= 0
                        && new_r < input.len() as isize
                        && new_c >= 0
                        && new_c < input[0].len() as isize
                    {
                        let new_r = new_r as usize;
                        let new_c = new_c as usize;

                        if !visited[new_r][new_c] {
                            visited[new_r][new_c] = true;

                            queue.push_back((new_r, new_c));
                        }
                    }
                }
            }
            let seen_asteroids = vectors.len();
            if seen_asteroids as i64 > max_asteroids {
                max_asteroids = seen_asteroids as i64;
                best_row = r1;
                best_col = c1;
            }
        }
    }
    println!("Part1: {max_asteroids}, r: {best_row}, c: {best_col}");
}

#[cfg(test)]
mod tests {
    use super::*;

    #[test]
    fn test_is_same_direction() {
        let line = Vector::new((6, 7), (3, 9));

        let other1 = Vector::new((4, 4), (1, 6));
        let other2 = Vector::new((6, 7), (0, 11));
        let other3 = Vector::new((6, 7), (9, 5));
        let other4 = Vector::new((0, 0), (5, 5));

        assert!(line.is_same_direction(&other1));
        assert!(line.is_same_direction(&other2));
        assert!(!line.is_same_direction(&other3));
        assert!(!line.is_same_direction(&other4));
    }
}
