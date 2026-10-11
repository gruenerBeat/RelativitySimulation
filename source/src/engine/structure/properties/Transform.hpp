#ifndef TRANSFORM_PROPERTY_HPP
#define TRANSFORM_PROPERTY_HPP

#include "../../math/Matrix.hpp"
#include "../Property.hpp"
#include "PropertyType.hpp"

class Transform : public Property {

  Matrix<4, 4> affineTransformation;

public:
  static inline Vector<3> UP = Vector<3>({0, 1, 0});
  static inline Vector<3> DOWN = Vector<3>({0, -1, 0});
  static inline Vector<3> RIGHT = Vector<3>({1, 0, 0});
  static inline Vector<3> LEFT = Vector<3>({-1, 0, 0});
  static inline Vector<3> FORWARD = Vector<3>({0, 0, 1});
  static inline Vector<3> BACKWARD = Vector<3>({0, 0, -1});

  Transform() : Property("Transform", TRANSFORM) {
    affineTransformation =
        Matrix<4, 4>({{1, 0, 0, 0}, {0, 1, 0, 0}, {0, 0, 1, 0}, {0, 0, 0, 1}});
  }

  void reset() {
    affineTransformation =
        Matrix<4, 4>({{1, 0, 0, 0}, {0, 1, 0, 0}, {0, 0, 1, 0}, {0, 0, 0, 1}});
  }

  Vector<3> getPosition() {
    return Vector<3>({affineTransformation(0, 3), affineTransformation(1, 3),
                      affineTransformation(2, 3)});
  }

  void setPostion(Vector<3> position) {
    affineTransformation(0, 3) = position(0);
    affineTransformation(1, 3) = position(1);
    affineTransformation(2, 3) = position(2);
  }

  void translate(Vector<3> displacement) {
    affineTransformation(0, 3) += displacement(0);
    affineTransformation(1, 3) += displacement(1);
    affineTransformation(2, 3) += displacement(2);
  }

  void transform(Matrix<4, 4> transformation) {
    affineTransformation = transformation * affineTransformation;
  }

  Matrix<4, 4> getWorldToLocalTransformation() { return affineTransformation; }

  void lookAt(Vector<3> pos) {
    Vector<3> newZ = (pos - getPosition()).normalized(1);
    Vector<3> newX = cross3(newZ, UP).normalized(1);
    Vector<3> newY = cross3(newX, newZ).normalized(1);
    affineTransformation =
        Matrix<4, 4>({{newX(0), newY(0), newZ(0), affineTransformation(0, 3)},
                      {newX(1), newY(1), newZ(1), affineTransformation(1, 3)},
                      {newX(2), newY(2), newZ(2), affineTransformation(2, 3)},
                      {0, 0, 0, 1}});
  }

  void tick() override {}
  void initialize() override {}
};

#endif
