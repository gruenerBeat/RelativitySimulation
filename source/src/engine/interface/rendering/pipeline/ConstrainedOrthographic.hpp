#ifndef CONSTRAINEDORTHOGRAPHIC_RENDERER_HPP
#define CONSTRAINEDORTHOGRAPHIC_RENDERER_HPP

#include "Renderer.hpp"
class ConstrainedOrthoRenderer : public Renderer {

public:
  ConstrainedOrthoRenderer(int width, int height) : Renderer(width, height) {}

  void Setup(World world, CameraObj cam) {}

  Texture Render(World world, CameraObj cam) { return Texture(w, h); }

  void Update(World world, CameraObj cam) {}
};

#endif
