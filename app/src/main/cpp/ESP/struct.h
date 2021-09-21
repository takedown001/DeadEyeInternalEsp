#ifndef STRUCT_H
#define STRUCT_H

#include <string>

#define maxplayerCount 100
#define maxgrenadeCount 10


class Color {

public:
    float r;
    float g;
    float b;
    float a;

    Color() {
        this->r = 0;
        this->g = 0;
        this->b = 0;
        this->a = 0;
    }

    Color(float r, float g, float b, float a) {
        this->r = r;
        this->g = g;
        this->b = b;
        this->a = a;
    }

    Color(float r, float g, float b) {
        this->r = r;
        this->g = g;
        this->b = b;
        this->a = 225;
    }

    static Color Clear() {
        return Color(0, 0, 0, 0);
    }

    static Color Black() {
        return Color(0, 0, 0);
    }

    static Color BlackT() {
        return Color(0, 0, 0, 135);
    }

    static Color BlackTT() {
        return Color(0, 0, 0, 90);
    }

    static Color White() {
        return Color(255, 255, 255);
    }

    static Color Red() {
        return Color(255, 0, 0);
    }

    static Color RedTT() {
        return Color(255, 0, 0, 200);
    }


    static Color RedT() {
        return Color(255, 0, 0, 60);
    }


    static Color Yellow() {
        return Color(255, 255, 0, 250);
    }

    static Color Green() {
        return Color(0, 255, 0, 200);
    }

    static Color Yellow4() {
        return Color(255, 145, 0, 125);
    }

    static Color Green4() {
        return Color(0, 210, 0, 125);
    }

    static Color Orange1() {
        return Color(255, 155, 50, 100);
    }

    static Color OrangeTT() {
        return Color(255, 155, 50, 200);
    }

    static Color Green1() {
        return Color(0, 220, 0, 100);
    }

    static Color Orange2() {
        return Color(255, 155, 60, 75);
    }

    static Color Green2() {
        return Color(0, 255, 0, 75);
    }

    static Color GreenTT() {
        return Color(0, 255, 0, 200);
    }

    static Color Orange3() {
        return Color(255, 255, 0, 50);
    }

    static Color Orange() {
        return Color(255, 155, 0, 255);
    }

    static Color OrangeD() {
        return Color(255, 65, 0, 255);
    }

    static Color Green3() {
        return Color(0, 255, 0, 50);
    }

    static Color Coral() {
        return Color(240, 128, 128);
    }

    static Color Cyan() {
        return Color(0, 255, 255);
    }

    static Color CyanT() {
        return Color(0, 255, 255, 60);
    }

    static Color Blue() {
        return Color(0, 0, 255);
    }

    static Color Purple() {
        return Color(128, 0, 128);
    }

    static Color Maroon() {
        return Color(128, 0, 0);
    }

    static Color Grey() {
        return Color(195, 190, 190);
    }

    static Color Gold() {
        return Color(255, 190, 0);
    }

    static Color SmokeyBlack() {
        return Color(25, 0, 15);
    }

    static Color Crimson() {
        return Color(225, 35, 55);
    }

    static Color Magenta() {
        return Color(255, 20, 147);
    }

    static Color MagentaD() {
        return Color(255, 192, 203);
    }

    static Color Aero() {
        return Color(189, 59, 12);
    }

    static Color Almond() {
        return Color(235, 220, 205);
    }

    static Color Ice() {
        return Color(240, 250, 255);
    }

    static Color Alien() {
        return Color(130, 220, 5);
    }

    static Color AlienT() {
        return Color(130, 220, 5, 60);
    }

    static Color RedLight() {
        return Color(255, 0, 0, 100);
    }

    static Color YellowLight() {
        return Color(255, 255, 0, 100);
    }

};

class Rect {
public:
    float x;
    float y;
    float width;
    float height;

    Rect() {
        this->x = 0;
        this->y = 0;
        this->width = 0;
        this->height = 0;
    }

    Rect(float x, float y, float width, float height) {
        this->x = x;
        this->y = y;
        this->width = width;
        this->height = height;
    }

    bool operator==(const Rect &src) const {
        return (src.x == this->x && src.y == this->y && src.height == this->height &&
                src.width == this->width);
    }

    bool operator!=(const Rect &src) const {
        return (src.x != this->x && src.y != this->y && src.height != this->height &&
                src.width != this->width);
    }
};

struct Vector33 {
    float x, y, z;
};

class Vector22 {
public:
    float x;
    float y;

    Vector22() {
        this->x = 0;
        this->y = 0;
    }

    Vector22(float x, float y) {
        this->x = x;
        this->y = y;
    }

    static Vector22 Zero() {
        return Vector22(0.0f, 0.0f);
    }

    bool operator!=(const Vector22 &src) const {
        return (src.x != x) || (src.y != y);
    }

    Vector22 &operator+=(const Vector22 &v) {
        x += v.x;
        y += v.y;
        return *this;
    }

    Vector22 &operator-=(const Vector22 &v) {
        x -= v.x;
        y -= v.y;
        return *this;
    }
};


#endif