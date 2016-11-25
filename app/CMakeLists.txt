cmake_minimum_required(VERSION 3.4.1)
set(lib_src_DIR ${CMAKE_SOURCE_DIR}/src/main/jniLibs/${ANDROID_ABI})
include_directories(${CMAKE_SOURCE_DIR}/src/main/cpp/include)

add_library(ffmpeg3 SHARED IMPORTED)
set_target_properties(ffmpeg3 PROPERTIES IMPORTED_LOCATION
                          ${lib_src_DIR}/libffmpeg.so)

add_library( native-lib SHARED
             src/main/cpp/native-lib.cpp )

find_library( log-lib
              log )
target_link_libraries(native-lib
                      log
                      android
                      ffmpeg3 )