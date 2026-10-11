#include "Vector.hpp"

Vector<3> cross3(const Vector<3> &a, const Vector<3> &b) {
  return Vector<3>({a(1) * b(2) - a(2) - b(1), a(2) * b(0) - a(0) - b(2),
                    a(0) * b(1) - a(1) * b(0)});
}
