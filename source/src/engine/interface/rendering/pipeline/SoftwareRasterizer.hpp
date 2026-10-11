#ifndef SOFTWARE_RENDERER_HPP
#define SOFTWARE_RENDERER_HPP

#include "../../../logic/GameState.hpp"
#include "../../../logic/Main.hpp"
#include "Renderer.hpp"
#include <cmath>
#include <cstdio>

class Software : public Renderer {

public:
  Software(int width, int height) : Renderer(width, height) {}
  void Setup(World world, CameraObj cam) {}

  Texture Render(World world, CameraObj cam) {
    Texture tex(w, h);
    for (int i = 0; i < w; i++) {
      for (int j = 0; j < h; j++) {
        tex.setPixel(i, j,
                     Color(((double)i / w) * 255.0, ((double)j / h) * 255.0,
                           255 * std::sin(gameState()->frame / 0.01)));
      }
    }

    return tex;
  }

  void Update(World world, CameraObj cam) {}
};

#endif
