package com.thirteen_back.repository;


import com.thirteen_back.entity.ChattingRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChattingRoomRepository extends JpaRepository<ChattingRoom, String > {

}
