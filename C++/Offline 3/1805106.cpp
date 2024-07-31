#include<iostream>
#include<cstring>
using namespace std;


class StringMath
{
    char *p;
public:
    //Add necessary constructors and destructors and functions
    StringMath(const char *s);
    StringMath(int n=0);
    StringMath(const StringMath &obj);
    StringMath operator+(StringMath obj);
    StringMath operator-(StringMath obj);
    StringMath operator+(int n);
    friend StringMath operator+(int n,StringMath obj);
    StringMath operator=(StringMath obj);
    bool operator>(StringMath obj);
    bool operator>(int n);
    StringMath operator++(int unused);
    friend ostream& operator<<(ostream &s,StringMath str);
    ~StringMath();
};
int main()
{
    StringMath S1;
    StringMath S2("123");
    StringMath S3("757");
    StringMath S4("220");
    StringMath S5;
    int n=345;
    S1=S4;
    //Print the character string of S1 and S4
    cout<<"S1: "<<S1<<endl;
    cout<<"S4: "<<S4<<endl;
    S1=S2+S3+S4;
    //Print the character string of S1, S2, S3 and S4, where S1 contains the character string: “1100”
    cout<<"S1: "<<S1<<endl;
    cout<<"S2: "<<S2<<endl;
    cout<<"S3: "<<S3<<endl;
    cout<<"S4: "<<S4<<endl;
    S5=S4=S3;
    //Print the character string of S5, S4 and S3
    cout<<"S3: "<<S3<<endl;
    cout<<"S4: "<<S4<<endl;
    cout<<"S5: "<<S5<<endl;
    if(S3 > n )
    {
        S5= S3+n;
    }
    // Print the character string of S5, where S5 contains the character string: “1102”
    cout<<"S5: "<<S5<<endl;
    S5= n+S2;
    // Print the character string of S5, where S5 contains the character string: “468”
    cout<<"S5: "<<S5<<endl;
    if(S5 > S2)
    {
        S5++; //Assume prefix increment
    }
    // Print the character string of S5, where S5 contains the character string: “469”
    cout<<"S5: "<<S5<<endl;
}


StringMath::StringMath(const char *s)
{
    int l=strlen(s);
    if(l!=0&&s[0]=='-'){
        cout<<"INVALID INPUT"<<endl;
        exit(1);
    }
    p=new char[l+1];
    strcpy(p,s);
}
StringMath::StringMath(int n)
{
    if(n<0){
        cout<<"INVALID INPUT"<<endl;
        exit(1);
    }
    if(n==0){
        p=new char[2];
        p[0]='0';
        p[1]='\0';
        return;
    }
    char temp[11];
    int l=0;
    while(n>0){
        temp[l++]=n%10+'0';
        n/=10;
    }
    p=new char[l+1];
    for(int i=0;i<l;i++)p[i]=temp[l-i-1];
    p[l]='\0';
}

StringMath::StringMath(const StringMath &obj)
{
    p=new char[strlen(obj.p)+1];
    strcpy(p,obj.p);
}
StringMath StringMath::operator+(StringMath obj)
{
    int l1=strlen(p);
    int l2=strlen(obj.p);
    int mx=(l1>l2?l1:l2)+1;
    char *temp=new char[mx+1];
    int carry=0;
    temp[mx]='\0';
    while(l1>0&&l2>0){
        carry+=(p[--l1]-'0'+obj.p[--l2]-'0');
        temp[--mx]=carry%10+'0';
        carry/=10;
    }
    while(l1>0){
        carry+=(p[--l1]-'0');
        temp[--mx]=carry%10+'0';
        carry/=10;
    }
    while(l2>0){
        carry+=(obj.p[--l2]-'0');
        temp[--mx]=carry%10+'0';
        carry/=10;
    }
    if(carry!=0){
        temp[--mx]=carry+'0';
    }
    StringMath S(temp+mx);
    delete[] temp;
    return S;
}
StringMath StringMath::operator-(StringMath obj)
{
    if(obj>*this){
        cout<<"INVALID INPUT"<<endl;
        exit(1);
    }
    int l1=strlen(p);
    int l2=strlen(obj.p);
    char *temp=new char[l1+1];
    int carry=0;
    temp[l1]='\0';
    while(l2>0){
        carry+=(p[--l1]-obj.p[--l2]);
        if(carry<0){
            carry+=10;
            temp[l1]=carry+'0';
            carry=-1;
        }
        else{
            temp[l1]=carry+'0';
            carry=0;
        }
    }
    while(l1>0){
        carry+=(p[--l1]-'0');
        if(carry<0){
            carry+=10;
            temp[l1]=carry+'0';
            carry=-1;
        }
        else{
            temp[l1]=carry+'0';
            carry=0;
        }
    }
    while(temp[l1]=='0')l1++;
    if(temp[l1]=='\0'){
        delete[] temp;
        return StringMath(0);
    }
    StringMath S(temp+l1);
    delete[] temp;
    return S;
}
StringMath StringMath::operator+(int n){
    if(n<0)return *this-StringMath(-n);
    return *this+StringMath(n);
}
StringMath operator+(int n,StringMath obj){
    if(n<0)return obj-StringMath(-n);
    return obj+n;
}
bool StringMath::operator>(StringMath obj)
{
    int l1=strlen(p),l2=strlen(obj.p);
    if(l1!=l2)return l1>l2;
    for(int i=0;i<l1;i++){
        if(p[i]!=obj.p[i])return p[i]>obj.p[i];
    }
    return false;
}
bool StringMath::operator>(int n)
{
    if(n<0)return true;
    return *this>StringMath(n);
}
StringMath StringMath::operator=(StringMath obj){
    delete[] p;
    p=new char[strlen(obj.p)+1];
    strcpy(p,obj.p);
    return *this;
}
ostream& operator<<(ostream &s,StringMath str)
{
    s<<str.p;
    return s;
}
StringMath StringMath::operator++(int unused){
    *this=*this+1;
    return *this;
}
StringMath::~StringMath(){
    delete[] p;
}
