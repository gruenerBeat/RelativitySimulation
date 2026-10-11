#ifndef PROPERTY_HPP
#define PROPERTY_HPP

#include "Object.hpp"
#include "properties/PropertyType.hpp"
#include <string>

class Property {

  std::string name;
  PropertyType type;
  Object parent;

public:
  Property(std::string name, PropertyType type)
      : name(name), type(type), parent("ParentObject") {}

  std::string getName() { return name; }

  PropertyType getType() { return type; }

  Object getParent() { return parent; }

  void setParent(Object obj) { parent = obj; }

  virtual void initialize() = 0;
  virtual void tick() = 0;
};

#endif
