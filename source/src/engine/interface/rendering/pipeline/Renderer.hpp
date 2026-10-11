#ifndef RENDERER_HPP
#define RENDERER_HPP

#include "../../../structure/World.hpp"
#include "../../../structure/objects/CameraObj.hpp"
#include "../types/Texture.hpp"

class Renderer {

public:
  int w;
  int h;

  Renderer(int width, int height) : w(width), h(height) {}

  virtual void Setup(World world, CameraObj cam) = 0;

  virtual Texture Render(World world, CameraObj cam) = 0;

  virtual void Update(World world, CameraObj cam) = 0;
};

#endif
