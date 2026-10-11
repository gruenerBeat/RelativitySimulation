#ifndef OBJECT_HPP
#define OBJECT_HPP

#include "properties/PropertyType.hpp"
#include <string>
#include <vector>

class Property;

class Object {

protected:
  int propertyCount;
  std::vector<Property *> properties;

public:
  std::string name;
  bool active;

  Object(std::string name) : name(name) {
    propertyCount = 0;
    active = true;
  }

  void addProperty(Property *property);

  bool hasProperty(std::string name);

  bool hasProperty(PropertyType type);

  Property *findProperty(std::string name);

  Property *findProperty(PropertyType type);

  void removeProperty(std::string name);

  void removeProperty(PropertyType type);

  void initialize();

  void tick();
};

void addProperty(Property *property);

bool hasProperty(std::string name);

bool hasProperty(PropertyType type);

Property *findProperty(std::string name);

Property *findProperty(PropertyType type);

void removeProperty(std::string name);

void removeProperty(PropertyType type);

void initialize();

void tick();

#endif
