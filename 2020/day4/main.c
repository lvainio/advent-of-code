#include <stdio.h>
#include <string.h>

int main(int argc, char **argv) {
    FILE *file;
    file = fopen("input.txt", "r");

    int part1 = 0;
    int part2 = 0;

    char line[100];
    int num_fields = 0;
    while(fgets(line, sizeof(line), file) != NULL) {    
        if (strcmp(line, "\n") == 0) {
            if (num_fields == 7)
                part1++;
            num_fields = 0;
            continue;
        }

        char *line_ptr;
        char *token = strtok_r(line, " ", &line_ptr);
        while (token != NULL) {
            char *field = strtok(token, ":");
            if (strcmp(field, "cid"))
                num_fields++;
            token = strtok_r(NULL, " ", &line_ptr);
        }
    }
    
    printf("part1: %d, part2:", part1);

    return 0;
}