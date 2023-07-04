package com.albatros.challenge;

import com.albatros.challenge.core.services.file.FileService;
import com.albatros.challenge.core.services.file.FileServiceImpl;
import com.albatros.challenge.core.services.spreadsheet.SpreadSheetService;
import com.albatros.challenge.core.services.spreadsheet.SpreadSheetServiceImpl;
import com.sun.tools.javac.Main;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

@SpringBootApplication
public class ChallengeApplication {

	public static void main(String[] args){SpringApplication.run(ChallengeApplication.class, args);}

}
