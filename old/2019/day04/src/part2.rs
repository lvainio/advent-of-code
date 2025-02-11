fn valid_password(password: i32) -> bool {
    let mut digits = Vec::new();
    let mut n = password;
    while n > 0 {
        digits.push(n % 10);
        n /= 10;
    }
    digits.reverse();
    for window in digits.windows(2) {
        if window[0] > window[1] {
            return false;
        }
    }

    if digits[0] > digits[1]
        || digits[1] > digits[2]
        || digits[2] > digits[3]
        || digits[3] > digits[4]
        || digits[4] > digits[5]
    {
        return false;
    }

    if (digits[0] == digits[1] && digits[1] != digits[2])
        || (digits[1] == digits[2] && digits[1] != digits[0] && digits[2] != digits[3])
        || (digits[2] == digits[3] && digits[2] != digits[1] && digits[3] != digits[4])
        || (digits[3] == digits[4] && digits[3] != digits[2] && digits[4] != digits[5])
        || (digits[4] == digits[5] && digits[4] != digits[3])
    {
        return true;
    }

    false
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
