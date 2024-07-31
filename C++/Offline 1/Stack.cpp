#include <iostream>
#include <cstdlib>
using namespace std;

class Stack
{
    Array s;
    int size;
    int top;
    // you are not allowed to add any field; you can only add methods
public:
    // your code
    Stack()
    {
        size=top=0;
    }
    Stack(int len)
    {
        size=len;
        top=0;
        s.setLength(len);
    }
    void clone(Stack a)
    {
        while(!isEmpty())pop();
        size=a.size;
        s.setLength(size);
        while(!a.isEmpty())push(a.pop());
    }
    bool isFull()
    {
        return top>=size;
    }
    bool isEmpty()
    {
        return top==0;
    }
    void push(int x)
    {
        if(isFull())
        {
            cout << "Stack is full" << endl;
            exit(0);
        }
        // your code
        s.setElementAt(top++,x);
    }
    int pop()
    {
        if(isEmpty())
        {
            cout << "Stack is empty" << endl;
            exit(0);
        }
        // your code
        return s.getElementAt(--top);
    }
    // your code
};
