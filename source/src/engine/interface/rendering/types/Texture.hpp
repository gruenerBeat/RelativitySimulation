#ifndef TEXTURE_HPP
#define TEXTURE_HPP

#include "../../../logic/Debug.hpp"
#include "Color.hpp"
#include <cassert>

class Texture {

  int w;
  int h;
  Color *colors;

public:
  Texture(int width, int height) : w(width), h(height) {
    colors = new Color[w * h];
    for (int i = 0; i < w; i++) {
      for (int j = 0; j < h; j++) {
        colors[i * h + j] = Color();
      }
    }
  }

  ~Texture() { delete[] colors; }

  int getWidth() { return w; }

  int getHeight() { return h; }

  Color getPixel(int x, int y) {
    if (x < 0 || x >= w || y < 0 || y >= h) {
      warning("Index out of Texture Bounds(Texture.hpp:getPixel(int, int))");
      return Color(0, 0, 0);
    }
    return colors[x * h + y];
  }

  void setPixel(int x, int y, Color color) {
    if (x < 0 || x >= w || y < 0 || y >= h) {
      warning(
          "Index out of Texture Bounds(Texture.hpp:setPixel(int, int, Color))");
    }
    colors[x * h + y] = color;
  }
};

#endif
