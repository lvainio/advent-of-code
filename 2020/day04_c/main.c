#include <ctype.h>
#include <stdio.h>
#include <stdlib.h> 
#include <string.h>

int is_valid_part1(char *field) {
    char *token = strtok(field, ":");
    if (strcmp(token, "cid"))
        return 1;
    return 0;
}

int is_valid_part2(char *field) {
    char *id = strtok(field, ":");
    char *value = strtok(NULL, ":"); 

    value[strcspn(value, "\n")] = 0;

    if(!strcmp(id, "byr")) { // 1920 <= byr <= 2002
        int birthyear = atoi(value);
        if (birthyear >= 1920 && birthyear <= 2002) {
            return 1;
        }

    } else if (!strcmp(id, "iyr")) { // 2010 <= iyr <= 2020
        int issueyear = atoi(value);
        if (issueyear >= 2010 && issueyear <= 2020) {
            return 1;
        }

    } else if (!strcmp(id, "eyr")) { // 2020 <= eyr <= 2030
        int issueyear = atoi(value);
        if (issueyear >= 2020 && issueyear <= 2030) {
            return 1;
        }

    } else if (!strcmp(id, "cid")) { // ignore cid
        return 0;
    }

    else if (!strcmp(id, "pid")) { // 9 digit number
        if (strlen(value) != 9) {
            return 0;
        }
        while (*value) {
            if (!isdigit((unsigned char)*value)) {
                return 0;
            }
            value++;
        }
        return 1;
    }

    else if (!strcmp(id, "ecl")) {
        if (!strcmp(value, "amb") || !strcmp(value, "blu") || 
           !strcmp(value, "brn") || !strcmp(value, "gry") ||
           !strcmp(value, "grn") || !strcmp(value, "hzl") || 
           !strcmp(value, "oth")) {
            return 1;
        }
    }

    else if (!strcmp(id, "hcl")) {
        if (value[0] != '#' || strlen(value) != 7) {
            return 0;
        }
        value++;
        while (*value) {
            if (!isdigit((unsigned char)*value) && 
                !('a' <= *value && *value <= 'f')) {
                return 0;
            }
            value++;
        }
        return 1;
    }

    else if (!strcmp(id, "hgt")) { // 150-193, 59-76
        int length = strlen(value);
        if (length <= 3) {
            return 0;
        }

        const char *unit = value + length - 2;
    
        if (!strcmp(unit, "in")) {
            value[length-2] = '\0';
            int inches = atoi(value);
            if (inches >= 59 && inches <= 76) {
                return 1;
            }

        } else if (!strcmp(unit, "cm")) {
            value[length-2] = '\0';
            int cms = atoi(value);
            if (cms >= 150 && cms <= 193) {
                return 1;
            }
        }
    }

    return 0;
}

int main(int argc, char **argv) {
    FILE *file;
    file = fopen("input.txt", "r");

    int part1 = 0;
    int part2 = 0;

    int num_fields_valid_1 = 0;
    int num_fields_valid_2 = 0;
    
    char line[100];
    while(fgets(line, sizeof(line), file) != NULL) {    
        if (strcmp(line, "\n") == 0) {
            if (num_fields_valid_1 == 7)
                part1++;
            if (num_fields_valid_2 == 7)
                part2++;
            num_fields_valid_1 = 0;
            num_fields_valid_2 = 0;
            continue;
        }

        char *line_ptr;
        char *token = strtok_r(line, " ", &line_ptr);
        while (token != NULL) {
            char copy[50];
            strcpy(copy, token);
            if (is_valid_part1(token))
                num_fields_valid_1++;
            if (is_valid_part2(copy))
                num_fields_valid_2++;
            token = strtok_r(NULL, " ", &line_ptr);
        }
    }
    
    printf("part1: %d, part2: %d", part1, part2);

    return 0;
}