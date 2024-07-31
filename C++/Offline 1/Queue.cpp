#include <iostream>
#include <cstdlib>
using namespace std;

class Queue
{
    Array s;
    int size;
    int front;
    int rear;
    // you are not allowed to add any field; you can only add methods
public:
    // your code
    Queue()
    {
        size=front=rear=0;
    }
    Queue(int x)
    {
        size=x;front=rear=0;
        s.setLength(x);
    }
    void clone(Queue x)
    {
        size=x.size,front=x.front,rear=x.rear;
        s.clone(x.s);
    }
    bool isFull()
    {
        return rear-front>=size;
    }
    bool isEmpty()
    {
        return rear<=front;
    }
    void enqueue(int x)
    {
        if(isFull())
        {
            cout << "Queue is full" << endl;
            exit(0);
        }
        // your code
//        s.setLength(s.getLength()+1);
        s.setElementAt((rear++)%size,x);
    }
    int dequeue()
    {
        if(isEmpty())
        {
            cout << "Queue is empty" << endl;
            exit(0);
        }
        // your code
        return s.getElementAt((front++)%size);
    }
    // your code
};
