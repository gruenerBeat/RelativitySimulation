#ifndef DEBUG_HPP
#define DEBUG_HPP

#include <ctime>
#include <string>

#define LOG_PATH "latest.log"

#define LOG_INFO true
#define LOG_WARNING true
#define LOG_ERROR true

#define REPORT_INFO true
#define REPORT_WARNING true
#define REPORT_ERROR true

void updateTime();
void info(std::string info);
void warning(std::string warning);
void error(std::string error, int code);

#endif
