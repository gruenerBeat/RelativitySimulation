#ifndef CAMERA_OBJECT_HPP
#define CAMERA_OBJECT_HPP

#include "../Object.hpp"
#include "../properties/Camera.hpp"
#include "../properties/Transform.hpp"

class CameraObj : public Object {

public:
  CameraObj(std::string name, int w, int h, double fov, double sensorDim)
      : Object(name) {
    Transform transform;
    Camera cam(fov, w, h, sensorDim);
    addProperty(&transform);
    addProperty(&cam);
  }
};

#endif
