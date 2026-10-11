#ifndef GAMESTATE_HPP
#define GAMESTATE_HPP

#include "../interface/rendering/Screen.hpp"
#include "../interface/rendering/pipeline/RenderType.hpp"
#include "../interface/rendering/pipeline/Renderer.hpp"
#include "../structure/World.hpp"
#include <cstdio>
#include <string>

class Software;

struct GameState {
  Screen screen;
  int w;
  int h;
  std::string name;
  RenderType rt;
  Renderer *renderer;
  long frame = 0;
  World world;
  CameraObj mainCam;
  double fov;

  GameState(std::string name, RenderType renderType, int windowWidth,
            int windowHeight, double fov)
      : name(name), w(windowWidth), h(windowHeight), rt(renderType),
        screen(name, windowWidth, windowHeight), world("Default World"),
        fov(fov), mainCam("Main Camera", fov, w, h, 1) {
    world.addObject(mainCam);
  }
};

#endif
