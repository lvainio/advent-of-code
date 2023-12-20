#include <iostream>
#include <fstream>
#include <string>
#include <vector>
#include <limits>
#include <algorithm>


long long part1(const std::vector<long long> numbers) {
    for (int i = 25; i < numbers.size(); i++) {
        bool found = false;
        long long target = numbers[i];

        for (int j = i - 25; j < i; j++) {
            for (int k = j + 1; k < i; k++) {
                if (numbers[j] + numbers[k] == target) {
                    found = true;
                    break;
                }
            }
        }
        if (!found) {
            return target;
        }
    }
    return -1;
}

long long part2(const std::vector<long long> numbers, long long target) {
    for (int size = 2; size < numbers.size(); size++) {
        for (int i = 0; i < numbers.size() - size + 1; i++) {
            long long sum = 0;
            for (int j = i; j < size + i; j++) {
                sum += numbers[j];
            }
            if (sum == target) {
                long long max = std::numeric_limits<long long>::min();
                long long min = std::numeric_limits<long long>::max();
                for (int j = i; j < i + size; j++) {
                    max = std::max(max, numbers[j]);  
                    min = std::min(min, numbers[j]); 
                }
                return max + min;
            }
        } 
    }
    return -1;
}

int main(int argc, char **argv) {
    std::fstream file;
    file.open("input.txt", std::ios::in);

    std::vector<long long> numbers;
    if (file.is_open()) { 
        std::string line;
        while (getline(file, line)) { 
            numbers.push_back(std::stoll(line));
        }
        file.close(); 
    }

    long long p1 = part1(numbers);
    long long p2 = part2(numbers, p1);

    std::cout << "part1: " << p1 << std::endl;
    std::cout << "part2: " << p2 << std::endl;

    return 0;
}