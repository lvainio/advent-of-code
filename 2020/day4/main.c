#include <stdio.h>

char *fields[] = { "byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid", "cid" };
char *optional[] = { "cid" };

int seen_fields[] = { 0, 0, 0, 0, 0, 0, 0, 0 };

int main(int argc, char **argv) {

    FILE *file;
    file = fopen("input.txt", "r");

    char *text;
    int total = 0;

    while(fgets(text, 140, file) != NULL) { // newline included in string, remember that when checking for len
        if (strcmp(text,"\n") == 0) { // blank line means new passport
            printf("blank line");
            // check if all seen fields excep cid = 1
            // if true then increment total
            // reset all seen fields to 0
            // continue;
        }

        // process a line of fields. 
        // increment seen_fields if we see a field for each field in line. 
    }

    printf("%d", total);

    return 0;
}