#include<iostream>
#define UNDEFINED 0
#define LINE 1
#define RECTANGLE 2
#define CUBE 3

using namespace std;


class Shape
{
    int type;
public:
    Shape(int type=UNDEFINED)
    {
        this->type = type;
        cout<<"In Shape with Type "<<type<<endl;
    }

    virtual int area()
    {
        cout<<"In Base Class, Overload this function in derived class"<<endl;
        return -1;
    }

    virtual int volume()
    {
        cout<<"In Base Class, Overload this function in derived class"<<endl;
        return -1;
    }

    int get_type(){ return type; }
    void set_type(int type){ this->type = type; }
    ~Shape()
    {
        cout<<"Destructing Shape"<<endl;
    }
};


class Line: public Shape
{
public:
    int length;
    Line(int type, int length):Shape(type)
    {
        this->length = length;
        cout<<"In Line with length "<<length<<endl;
    }

    ///override area() and volume()
    int area()
    {
        cout<<"Area is "<<0<<endl;
        return 0;
    }

    int volume()
    {
        cout<<"Volume is "<<0<<endl;
        return 0;
    }

    ///setter getter if required

    ~Line()
    {
        cout<<"Destructing Line"<<endl;
    }
};

class Rectangle: private Line
{
    int width;
public:
    Rectangle(int type, int length, int width):Line(type,length)
    {
        this->width = width;
        cout<<"In Rectangle with width "<<width<<endl;
    }

    ///override area() and volume()
    int area()
    {
        int x=length*width;
        cout<<"Area is "<<x<<endl;
        return x;
    }

    int volume()
    {
        cout<<"Volume is "<<0<<endl;
        return 0;
    }

    ///setter getter if required
    int getWidth()
    {
        return width;
    }
    void setWidth(int width)
    {
        this->width = width;
    }
    int getLength()
    {
        return length;
    }
    void setLength(int length)
    {
        this->length = length;
    }

    ~Rectangle()
    {
        cout<<"Destructing Rectangle"<<endl;
    }
};

class Cube: private Rectangle
{
    int height;
public:
    Cube():Rectangle(CUBE,0,0)
    {
        height = 0;
        /// set length and width
    }

    Cube(int type, int length, int width, int height):Rectangle(type,length,width)
    {
        this->height = height;
        cout<<"In Cube with width "<<height<<endl;
    }

    ///override area() and volume()
    int area()
    {
        int x=2*(getLength()*getWidth()+getLength()*height+height*getWidth());
        cout<<"Area is "<<x<<endl;
        return x;
    }

    int volume()
    {
        int x=getLength()*getWidth()*height;
        cout<<"Volume is "<<x<<endl;
        return x;
    }

    ///setter getter if required

    ~Cube()
    {
        cout<<"Destructing Cube"<<endl;
    }
};


int main()
{
    Shape s;
    s.area();
    ///In Base Class, Overload this function in derived class
    s.volume();
    ///In Base Class, Overload this function in derived class

    Line l(LINE, 5);
    ///Implement area and volume function
    l.area();
    ///Area is 0
    l.volume();
    ///Volume is 0

    Rectangle r(RECTANGLE, 5, 2);
    ///Notice that access modifier is private
    r.area();
    ///Area is 10
    r.volume();
    ///Volume is 0

    Cube c(CUBE, 5, 2, 4);
    ///You can not access length due to private modifier in rectangle
    ///write necessary methods in rectangle to access length
    c.area();
    ///Area is 76
    c.volume();
    ///Volume is 40

    ///Demonstration of Virtual Function in Shape
    Shape *s1;
    s1 = &l;
    s1->area();
    ///Area is 0
    s1->volume();
    ///Volume is 0


    Shape* s2;
    s2 = &l;
    s2->area();
    ///Area is 0
    s2->volume();
    ///Volume is 0

    ///write destructor function and observe (by printing something) the order of constructor and destructor call

    return 0;
}
