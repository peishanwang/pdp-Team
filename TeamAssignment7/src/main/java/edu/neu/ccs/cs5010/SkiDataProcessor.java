//package edu.neu.ccs.cs5010;
//
//import com.univocity.parsers.csv.CsvParser;
//import com.univocity.parsers.csv.CsvParserSettings;
//import com.univocity.parsers.common.*;
//import com.univocity.parsers.common.processor.*;
//import com.univocity.parsers.common.record.*;
//import com.univocity.parsers.conversions.*;
//import com.univocity.parsers.csv.*;
//import org.testng.annotations.*;
//
//import java.util.List;
//
//public class SkiDataProcessor {
//
//  CsvParserSettings settings = new CsvParserSettings();
//  //the file used in the example uses '\n' as the line separator sequence.
//  //the line separator sequence is defined here to ensure systems such as MacOS and Windows
//  //are able to process this file correctly (MacOS uses '\r'; and Windows uses '\r\n').
//	settings.getFormat().setLineSeparator("\n");
//
//  // creates a CSV parser
//  CsvParser parser = new CsvParser(settings);
//
//  // parses all rows in one go.
//  List<String[]> allRows = parser.parseAll(getReader("/examples/example.csv"));
//
//
//}
