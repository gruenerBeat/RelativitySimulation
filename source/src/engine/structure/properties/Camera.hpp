#ifndef CAMERA_PROPERTY_HPP
#define CAMERA_PROPERTY_HPP

#include "../../math/Matrix.hpp"
#include "../../math/Polar.hpp"
#include "../Property.hpp"
#include "PropertyType.hpp"
#include "Transform.hpp"
#include <cmath>
#include <string>

class Camera : public Property {

  double fov;
  double aspectRatio;
  double focalLength;
  int w;
  int h;
  double sensorDimension;
  std::string camName;

public:
  Camera(double fov, int w, int h, double sensorDim)
      : Property("Camera", CAMERA), fov(fov), w(w), h(h),
        sensorDimension(sensorDim) {
    aspectRatio = (double)w / (double)h;
    focalLength = (sensorDimension / 2) * cot((fov * (M_PI / 180)) / 2);
  }

  std::string getName() { return camName; }

  double getFov() { return fov; }

  double getAspectRatio() { return aspectRatio; }

  double getFocalLength() { return focalLength; }

  void setName(std::string name) { camName = name; }

  void updateFov(double fov) {
    this->fov = fov;
    focalLength = (sensorDimension / 2) * cot((fov * (M_PI / 180)) / 2);
  }

  Matrix<3, 3> getProjection() {
    return Matrix<3, 3>({{focalLength, 0, (double)w / 2},
                         {0, focalLength, (double)h / 2},
                         {0, 0, 1}});
  }

  Matrix<4, 4> getWorldToLocal() {
    return dynamic_cast<Transform *>(getParent().findProperty(TRANSFORM))
        ->getWorldToLocalTransformation();
  }

  void initialize() override {}

  void tick() override {}
};

#endif
