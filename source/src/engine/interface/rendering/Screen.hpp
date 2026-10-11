#ifndef SCREEN_HPP
#define SCREEN_HPP

#include "types/Color.hpp"
#include "types/Texture.hpp"
#include <SDL2/SDL.h>
#include <SDL2/SDL_render.h>
#include <SDL2/SDL_video.h>
#include <string>

class Screen {

  SDL_Window *window;
  SDL_Renderer *renderer;
  std::string title;
  int w;
  int h;

public:
  Screen(std::string title, int width, int height)
      : title(title), w(width), h(height) {}

  void SDLSetup() {

    if (SDL_Init(SDL_INIT_VIDEO) < 0) {
      error("SDL setup failed(Screen.hpp:SDLSetup())", 1);
      return;
    }

    window = SDL_CreateWindow(title.c_str(), SDL_WINDOWPOS_UNDEFINED,
                              SDL_WINDOWPOS_UNDEFINED, w, h, 0);
    if (!window) {
      error("SDL Window creation failed(Screen.hpp:SDLSetup())", 11);
      return;
    }

    renderer = SDL_CreateRenderer(window, -1, 0);
    SDL_SetRenderDrawColor(renderer, 0, 0, 0, 0);
    SDL_RenderClear(renderer);
  }

  void Render(Texture texture) {
    SDL_RenderClear(renderer);
    for (int i = 0; i < w; i++) {
      for (int j = 0; j < h; j++) {
        Color c = texture.getPixel(i, j);
        SDL_SetRenderDrawColor(renderer, c.r, c.g, c.b, 255);
        SDL_RenderDrawPoint(renderer, i, j);
      }
    }
    SDL_RenderPresent(renderer);
  }

  void TerminateSDL() {
    if (renderer != nullptr) {
      SDL_RenderPresent(renderer);
      SDL_DestroyRenderer(renderer);
    }

    if (window != nullptr) {
      SDL_DestroyWindow(window);
    }
    SDL_Quit();
  }
};

#endif
