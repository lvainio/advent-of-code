#include <stdio.h>

int main(int argc, char **argv) {

    FILE *file;
    file = fopen("input.txt", "r");

    char *text;

    while(fgets(text, 140, file) != NULL) { // newline included in string, remember that when checking for len
        printf("%s", text);
    }

    return 0;
}