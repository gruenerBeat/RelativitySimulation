#include "../engine/logic/GameState.hpp"
#include "InitGame.hpp"

constexpr int width = 1280;
constexpr int height = 720;

GameState *initializeGame() {
  return new GameState("Example Game", SOFTWARE, 1280, 720, 90);
}
