fn valid_password(password: i32) -> bool {
    let mut digits = Vec::new();
    let mut n = password;
    while n > 0 {
        digits.push(n % 10);
        n /= 10;
    }
    digits.reverse();
    let mut has_pair = false;
    for window in digits.windows(2) {
        if window[0] > window[1] {
            return false;
        }
        if window[0] == window[1] {
            has_pair = true;
        }
    }
    has_pair
}

pub fn solve() {
    let mut num_valid = 0;
    for password in 193651..=649729 {
        if valid_password(password) {
            num_valid += 1;
        }
    }
    println!("Part1: {num_valid}");
}
