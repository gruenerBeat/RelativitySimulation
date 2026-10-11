#include "Debug.hpp"
#include "Main.hpp"
#include <ctime>
#include <iostream>

time_t timestamp;

void updateTime() { time(&timestamp); }

void info(std::string info) {
  if (REPORT_INFO) {
    std::cout << "[" << ctime(&timestamp) << "] " << "INFO: " << info
              << std::endl;
  }
}

void warning(std::string warning) {
  if (REPORT_WARNING) {
    std::cout << "[" << ctime(&timestamp) << "] " << "WARNING: " << warning
              << std::endl;
  }
}

void error(std::string error, int code) {
  if (REPORT_ERROR) {
    std::cout << "[" << ctime(&timestamp) << "] " << "ERROR: " << error
              << std::endl;
  }
  TerminateGame(code);
}
