#include "../logic/Debug.hpp"
#include "Object.hpp"
#include "Property.hpp"
#include <cstdio>

void Object::addProperty(Property *property) {
  if (!hasProperty(property->getType()))
    return;
  property->setParent(*this);
  properties.push_back(property);
  propertyCount++;
}

bool Object::hasProperty(std::string name) {
  for (int i = 0; i < propertyCount; i++) {
    if (properties.at(i)->getName() == name) {
      return true;
    }
  }
  return false;
}

bool Object::hasProperty(PropertyType type) {
  for (int i = 0; i < propertyCount; i++) {
    if (properties.at(i)->getType() == type) {
      return true;
    }
  }
  return false;
}

Property *Object::findProperty(std::string name) {
  for (int i = 0; i < propertyCount; i++) {
    if (properties.at(i)->getName() == name) {
      return properties.at(i);
    }
  }
  warning("No such Property on Object(Object.cpp:findProperty(std::string))");
  return nullptr;
}

Property *Object::findProperty(PropertyType type) {
  for (int i = 0; i < propertyCount; i++) {
    if (properties.at(i)->getType() == type) {
      return properties.at(i);
    }
  }
  warning("No such Property on Object(Object.cpp:findProperty(std::string))");
  return nullptr;
}

void Object::removeProperty(std::string name) {
  for (int i = 0; i < propertyCount; i++) {
    if (properties.at(i)->getName() == name) {
      properties.erase(properties.begin() + i);
      propertyCount--;
    }
  }
}

void Object::removeProperty(PropertyType type) {
  for (int i = 0; i < propertyCount; i++) {
    if (properties.at(i)->getType() == type) {
      properties.erase(properties.begin() + i);
      propertyCount--;
    }
  }
}

void Object::initialize() {
  for (int i = 0; i < propertyCount; i++) {
    properties.at(i)->initialize();
  }
}

void Object::tick() {
  for (int i = 0; i < propertyCount; i++) {
    properties.at(i)->tick();
  }
}
