#include "stdio.h"
#include "stdlib.h"
#include "string.h"

int readInput(char *argv);
void create(char *argv,char *str);
void append(char *argv,char *str);

int main(int argc, char *argv[]){
    if(argc<2 || argc>3){
        printf("Wrong input format detected.");
        exit(1);
    }

    int a = readInput(argv[1]);
    if (a){
        exit(1);
    }
    return 98;
}

int readInput(char *argv){
    FILE *output = fopen("output.txt","w");
    if(output == NULL){
        printf("Cannot open %s",argv);
        return 1;
    }
    FILE *input = fopen(argv,"r");
    if(input == NULL){
        printf("Cannot open %s",argv);
        return 1;
    }
    char str[300];
    int fNo=0;
    char **file = (char**)malloc(sizeof(char*));
    char **text = (char**)malloc(sizeof(char*));
    
    while(fscanf(input,"%[^\n]",str) != EOF)
    {
        int a = fgetc(input);
        if(str[0] == 'c'){              // C   R   E   A   T   E
            fprintf(output,"%s\n",str);
            if(fNo!=0){
                file = (char **) realloc(file,sizeof(char*)*(fNo+1));
                text = (char **) realloc(text,sizeof(char*)*(fNo+1));
            }

            char *curr;
            int p=0;
            char *ptr=str;                                      // ptr fileName start p-10 fileName length
            ptr=ptr+10;
            curr = str;

            if(str[8]=='n'){    //  -n                        // curr+4 msg start
                
                while(curr){
                    if(*curr == ' ' && *(curr+1) == '-' && *(curr+2)=='t'){
                        break;
                    }
                    
                    curr++;
                    p++;
                }
                if (p==9) {
                    printf("Error! No file name found.!\n");
                    continue;
                }
                
                file[fNo] = (char*)malloc(sizeof(char)*(p-9));
                strncpy(file[fNo],ptr,p-10);
                file[fNo][p-10]='\0';
                text[fNo] = (char*)malloc(sizeof(char)*(300));
                strcpy(text[fNo],curr+4);
                //p-10 file name lenght
                //curr+4 start of msg
                create(file[fNo],text[fNo]);
                fNo++;
            }
            else if (str[8]=='t') { //-t
                while(curr){                // file name start: curr+4 length totheEnd
                    if(*curr == ' ' && *(curr+1) == '-' && *(curr+2)=='n'){
                        break;
                    }
                    
                    curr++;
                    p++;
                }
                file[fNo] = (char*)malloc(sizeof(char)*(60));
                strcpy(file[fNo],curr+4);
                
                text[fNo] = (char*)malloc(sizeof(char)*(300));
                
                strncpy(text[fNo],ptr,p-10);
                text[fNo][p-10]='\0';
                
                //printf("_%s_%d_%s\n",ptr,p,curr+4);
                //printf("_%s_%s",text[fNo],file[fNo]);
                
                create(file[fNo],text[fNo]);
                fNo++;
            }
            
        }
        else if (str[0]=='d'){          // D   E   L   E   T   E
            fprintf(output,"%s\n",str);
        
            // strlen(str)-10 filename lenght
            if (remove(str+10) == 0) 
                printf("");
                //Deleted successfully
            else
                printf("Unable to delete the file");
            int i=0;
            while(i<fNo){
                if(strcmp(file[i],str+10)==0){
                    file[i] = NULL;
                    text[i] = NULL;
                    free(file[i]);
                    free(text[i]);
                    i=-1;
                    break;
                }
                i++;
            }
            if (i!=-1){
                printf("Cannot found file!\n");
            }
            

        }
        else if (str[2]=='m'){          //  R   E   M   O   V   E
            fprintf(output,"%s\n",str);
            //printf("%s\n",str+10);
            char *fname;
            int start,length;
            char *str2 = strtok(str," ");
            str2 = strtok(NULL," ");
            fname = strtok(NULL," ");
            //printf("%s\n",fname);
            str2 = strtok(NULL," ");
            str2 = strtok(NULL," ");
            start = atoi(str2);
            //printf("%d\n",start);
            str2 = strtok(NULL," ");
            str2 = strtok(NULL," ");
            length = atoi(str2);
            //printf("%d\n",length);
            //printf("%d\n",fNo);
            int a=0;
            while(a<fNo){
                if (file[a]) {
                    if (strcmp(file[a],fname)==0) {
                        //printf("buldum%s_%s\n",file[a],text[a]);
                        int b=0;
                        while(b<strlen(text[a])-length){
                            text[a][start+b] = text[a][start+b+length];
                            b++;
                        }
                        text[a][start+b]='\0';
                        create(file[a],text[a]);
                    }
                }
                a++;
            }
            
        }
        else if (str[1]=='p'){          //  A   P   P   E   N   D
            fprintf(output,"%s\n",str);
            int p=0;
            char *curr=str+10,*ptr=str+10;
            char *fname;
            if(str[8]=='n'){
                while(curr){
                    if(*curr == ' ' && *(curr+1) == '-' && *(curr+2) == 't'){
                        break;
                    }
                    curr++;
                    p++;
                }
                //printf("%s_\n",ptr);                 //ptr filename start, p length
                //printf("%s_\n",curr+4);        // curr+4 msg start, lenght totheEnd
                fname = (char*)malloc(sizeof(char)*(p+1));
                strncpy(fname,ptr,p);
                fname[p] = '\0';
                //printf("%s_\n",fname);
                int i= 0;
                while(i<fNo){
                    if(file[i]){
                        if(strcmp(file[i],fname)==0){
                            //printf("%s===%s:%s:%s\n",file[i],fname,text[i],curr+4);
                            strcat(text[i],curr+4);
                            //printf("%s\n",text[i]);
                            append(fname,curr+4);
                            break;
                        }
                    }
                    i++;
                }
                
                free(fname);
            }
            else if(str[8]=='t'){
                while(curr){
                    if(*curr == ' ' && *(curr+1) == '-' && *(curr+2) == 'n'){
                        break;
                    }
                    curr++;
                    p++;
                }
                fname = (char*)malloc(sizeof(char)*(50));
                //printf("%s_%d_\n",ptr,p);
                strcpy(fname,curr+4);
                char *newmsg=(char*)malloc(sizeof(char)*p+1);
                strncpy(newmsg,ptr,p);
                newmsg[p]='\0';
                int i=0;
                while(i<fNo){
                    if(file[i]){
                        if(strcmp(file[i],fname)==0){
                            //printf("buldum%s",file[i]);
                            strcat(text[i],newmsg);
                            append(fname,newmsg);
                            break;
                        }
                    }
                    i++;
                }
                free(fname);
                free(newmsg);
            }
        }
        else if (str[3]=='l'){          //  R   E   P   L   A   C   E
            fprintf(output,"%s\n",str);
            char * str2 =strtok(str," ");
            char *fname=(char*)malloc(sizeof(char)*300);
            char *oname=(char*)malloc(sizeof(char)*300);
            char *nname=(char*)malloc(sizeof(char)*300);
            
            while(str2=strtok(NULL," ")){
                //printf("%s",str2);
                if(strcmp(str2,"-n")==0){
                    str2=strtok(NULL," ");
                    strcpy(fname,str2);
                }
                else if(strcmp(str2,"-nw")==0){
                    str2=strtok(NULL," ");
                    strcpy(nname,str2);
                }
                else if(strcmp(str2,"-ow")==0){
                    str2=strtok(NULL," ");
                    strcpy(oname,str2);
                }
            }
            //printf("%s\n",fname);
            //printf("%s\n",oname);
            //printf("%s\n",nname);

            int i=0;
            while(i<fNo){
                if(file[i]){
                    if(strcmp(file[i],fname)==0){
                        //printf("%s\n",text[i]);
                        int j=0,fark=strlen(oname)-strlen(nname);
                        char *newtext=(char*)malloc(sizeof(char)*300);
                        //printf("%d\n",fark);
                        while( j < strlen(text[i])-strlen(oname)){
                            int k=0,isFound=1;
                            while(k<strlen(oname)){
                                if(text[i][j+k]!=oname[k]){
                                    isFound=0;
                                    break;
                                }
                                k++;
                            }
                            //text[i]+j (j. index of text[i]is) oname start
                            if(isFound==1){
                                //printf("%s_%d_\n",text[i]+j,j);
                                strncpy(newtext,text[i],j);
                                //newtext[j]='\0';
                                //printf("__%s_\n",newtext);
                                strcat(newtext,nname);
                                strcat(newtext,text[i]+j+strlen(oname));
                                strcpy(text[i],newtext);
                                //printf("__%s_\n__%s_\n\n",newtext,text[i]);
                                memset(newtext,'\0',strlen(newtext));
                                create(file[i],text[i]);
                            }
                            j++;
                        }


                        free(newtext);
                        break;
                    }
                }
                
                i++;
            }
            

            free(oname);
            free(nname);
            free(fname);
        }
        else if (str[0]=='p'){          //  P   R   I   N   T
            fprintf(output,"%s\n",str);
            char *type = strtok(str," ");
            type=strtok(NULL," ");
            if(type[1]=='a'){                   // -a
                int i=0;
                while(i<fNo){
                    if(file[i]){
                        fprintf(output,"Filename %d: %s\n",i+1,file[i]);
                    }
                    i++;
                }
            }
            else if(type[1]=='e'){              // -e
                type = strtok(NULL," ");
                int i=0;
                while(i<fNo){
                    if(file[i]){
                        //printf("%s?=%s_\n",file[i]+strlen(file[i])-strlen(type),type);
                        if(strcmp(file[i]+strlen(file[i])-strlen(type),type)==0){
                            char *filename = (char*)malloc(sizeof(char)*(strlen(file[i])-strlen(type)));
                            strncpy(filename,file[i],strlen(file[i])-strlen(type));
                            filename[strlen(file[i])-strlen(type)-1]='\0';
                            fprintf(output,"Filename %d: %s\n",i+1,filename);
                            fprintf(output,"Text: %s\n",text[i]);

                            free(filename);
                        }
                    }
                    i++;
                }
            }
            else if(type[1]=='c'){              // -c
                int i=0;
                while(i<fNo){
                    if(file[i]){
                        int j = strlen(file[i])-1;
                        while(file[i][j]!='.'){
                            j--;
                        }
                        char *filename=(char*)malloc(sizeof(char)*j+1);
                        strncpy(filename,file[i],j);
                        filename[j] = '\0';
                        fprintf(output,"Num: %d\n",i+1);
                        fprintf(output,"Name: %s\n",filename);
                        fprintf(output,"Text: %s\n",text[i]);
                    }
                    i++;
                }
            }
            else if(type[1]=='n'){              // -n forms
                type=strtok(NULL," ");
                char *fname=(char *)malloc(sizeof(char)*strlen(type));
                strcpy(fname,type);
                type=strtok(NULL," ");
                //printf("%s_%s_\n",fname,type);
                if(type[1]=='t'){                   // -t
                    int i=0;
                    while(i<fNo){
                        if(file[i]){
                            if(strcmp(file[i],fname)==0){
                                fprintf(output,"Text: %s\n",text[i]);
                            }
                        }
                        i++;
                    }
                }
                else if(strcmp(type,"-cw")==0){     // -cw
                    type=strtok(NULL," ");
                    //printf("%s\n",type);
                    int i=0;
                    while(i<fNo){
                        if(file[i]){
                            if(strcmp(fname,file[i])==0){
                                int j=0,totalFound=0;
                                while(j<strlen(text[i])-strlen(type)){
                                    int a=0;
                                    int isFound=1;
                                    while(a<strlen(type)){
                                        if(type[a]!=text[i][a+j]){
                                            isFound=0;
                                            break;
                                        }
                                        a++;
                                    }
                                    if(isFound==1){
                                        totalFound++;
                                    }
                                    //printf("_%c",text[i][j]);
                                    j++;
                                }
                                fprintf(output,"Text: %s\n",text[i]);
                                fprintf(output,"Number Of Occurrence of \"%s\" : %d\n",type,totalFound);
                            }
                        }
                        i++;
                    }
                }
                else if(strcmp(type,"-cs")==0){     // -cs
                    int i=0;
                    while(i<fNo){
                        if(file[i]){
                            if(strcmp(file[i],fname)==0){
                                char *txt = (char*)malloc(sizeof(char)*strlen(text[i]));
                                memcpy(txt,text[i],sizeof(char)*strlen(text[i]));
                                type=strtok(txt,".?!");
                                int totalsntce=0;
                                while(type=strtok(NULL,".?!")){
                                    totalsntce++;
                                }
                                fprintf(output,"Number Of Sentences : %d\n",totalsntce);
                                free(txt);
                            }
                        }
                        i++;
                    }
                }


                free(fname);
            }
            
        }


        if(feof(input)){
    		break;
		}
        
    }
    while(fNo-->0){
        if(file[fNo]){
            //printf("<%s><%s>\n",file[fNo],text[fNo]);
            free(file[fNo]);
            free(text[fNo]);
        }
    }
    
    free(file);
    free(text);
    fclose(input);
    fclose(output);
    return 0;
}

void create(char *argv,char *str)
{
    FILE *input = fopen(argv,"w");
    fprintf(input,"%s",str);
    fclose(input);
}

void append(char *argv,char *str)
{
    FILE *input = fopen(argv,"a");
    fprintf(input,"%s",str);
    fclose(input);
}