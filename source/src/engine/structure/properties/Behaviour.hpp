#include "../Property.hpp"
#include "PropertyType.hpp"
#include <string>

class Behaviour : Property {

  std::string name;

public:
  Behaviour(std::string name) : Property("Behaviour", BEHAVIOUR), name(name) {}

  virtual void tick() override = 0;

  virtual void initialize() override = 0;
};
