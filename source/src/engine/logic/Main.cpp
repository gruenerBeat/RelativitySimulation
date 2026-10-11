#include "../../game/InitGame.hpp"
#include "../interface/rendering/pipeline/ConstrainedOrthographic.hpp"
#include "../interface/rendering/pipeline/SoftwareRasterizer.hpp"
#include "Debug.hpp"
#include "GameState.hpp"
#include "Main.hpp"
#include <cstdio>
#include <cstdlib>
#include <ctime>
#include <thread>

bool running;
int exitCode = 0;

GameState *state;

void renderClock() {
  state->screen.SDLSetup();
  state->renderer->Setup(state->world, state->mainCam);
  SDL_Event event;

  while (running) {
    state->screen.Render(state->renderer->Render(state->world, state->mainCam));
    state->frame++;

    while (SDL_PollEvent(&event)) {
      if (event.type == SDL_QUIT) {
        TerminateGame(0);
      }
    }
  }
}

void gameClock() {
  state->world.initialize();
  while (running) {
    state->world.tick();
  }
}

void inputClock() {
  while (running) {
  }
}

int main() {
  updateTime();

  state = initializeGame();

  switch (state->rt) {
  case SOFTWARE:
    state->renderer = new Software(state->w, state->h);
    break;
  case CONSTRAINED_ORTHO:
    state->renderer = new ConstrainedOrthoRenderer(state->w, state->h);
    break;
  default:
    error("Invalid Rendertype(main.cpp:main())", 3);
    return 1;
  }

  running = true;

  std::thread renderWorker(renderClock);
  std::thread gameWorker(gameClock);

  std::thread inputWorker(inputClock);
  while (running)
    ;

  while (!renderWorker.joinable() || !gameWorker.joinable() ||
         !inputWorker.joinable())
    ;

  renderWorker.join();
  gameWorker.join();
  inputWorker.join();

  return exitCode;
}

GameState *gameState() { return state; }

void TerminateGame(int code) {
  exitCode = code;
  printf("Program exited with code %d\n", code);
  if (running) {
    running = false;
  } else {
    if (code != 0) {
      exit(EXIT_FAILURE);
    } else {
      exit(EXIT_SUCCESS);
    }
  }
}
