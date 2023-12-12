#include <stdio.h>
#include <string.h>

char *fields[] = { "byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid", "cid" };
char *optional[] = { "cid" };

int main(int argc, char **argv) {

    FILE *file;
    file = fopen("input.txt", "r");

    char *text;
    int total = 0;

    int num_fields_seen = 0;
    while(fgets(text, 140, file) != NULL) { // newline included in string, remember that when checking for len        
        if (strcmp(text,"\n") == 0) { // blank line means new passport
            if (num_fields_seen == 7) { // all except cid we dont care about cid. 
                total++;
            }
            num_fields_seen = 0;
            continue;
        }

        char *token = strtok(text, " ");
        while (token != NULL) {
            token = strtok(NULL, delimiters);
            char *t2 = strtok(token, ":");
            if strcmp(t2, "cid") {
                num_fields_seen++;
            }
        }
    }

    printf("%d", total);

    return 0;
}