#include "stdio.h"
#include "stdlib.h"

int main(int argc, char *argv[]){
    printf("Enter a number: ");
    int a;
    scanf("%d",&a);
    int *tList = (int*)malloc(a*sizeof(int));
    *tList = 0;
    *(tList+1) = 1;
    *(tList+2) = 2;
    int count = 0;
    if(a>0){
        while(count != a && count < 3){
            printf("%d",*(tList+count));
            count++;
            if(a!=count)printf(" ");
        }
        
    }
    if (a>3){
        while(count != a){
            *(tList+count) = *(tList+count-3) + *(tList+count-1) + *(tList+count-2);
            printf("%d",*(tList+count));
            count++;
            if(a!=count)printf(" ");
        }
    }
    free(tList);
    return 98;
}