#ifndef WORLD_HPP
#define WORLD_HPP

#include "../logic/Debug.hpp"
#include "Object.hpp"
#include <string>
#include <vector>

class World {

  bool active = false;
  std::string name;
  std::vector<Object> objects;

public:
  World(std::string name) : name(name) {}

  bool hasObject(std::string name) {
    if (objects.size() == 0) {
      warning("World does not contain any"
              "objects(World.hpp:hasObject(std::string))");
      return false;
    }
    for (Object o : objects) {
      if (o.name == name) {
        return true;
      }
    }
    return false;
  }

  Object findObject(std::string name) {
    for (Object o : objects) {
      if (o.name == name) {
        return o;
      }
    }
    warning("World does not contain this "
            "object(World.hpp:findObject(std::string))");
    return Object("ERROR");
  }

  void addObject(Object obj) {
    objects.push_back(obj);
    if (active) {

      obj.initialize();
    }
  }

  void initialize() {
    active = true;
    for (Object obj : objects) {
      obj.initialize();
    }
  }

  void tick() {
    if (active) {
      for (Object obj : objects) {
        obj.tick();
      }
    }
  }

  void unload() { active = false; }
};

#endif
