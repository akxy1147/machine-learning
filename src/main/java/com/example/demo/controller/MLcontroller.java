package com.example.demo.controller;
import jdk.internal.org.xml.sax.SAXException;
import org.dmg.pmml.FieldName;
import org.dmg.pmml.PMML;
import org.jpmml.evaluator.*;
import org.jpmml.evaluator.Evaluator;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.*;

import javax.xml.bind.JAXBException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController

public class MLcontroller {



    @PostMapping("/predict")
    public String predict(
            @RequestParam int feature1,
            @RequestParam int feature2,
            @RequestParam int feature3,
            @RequestParam int feature4,
            @RequestParam int feature5,
            @RequestParam int feature6
    )  throws Exception {
        // Load the PMML model file

        PMML pmml = new PMML();
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream("D:/毕设/springboot/src/main/java/com/example/demo/md.pmml");      // 需要引用python保存下来的pmml文件
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(inputStream == null){
            return null;
        }
        InputStream is = inputStream;
        try {
            pmml = org.jpmml.model.PMMLUtil.unmarshal(is);
        } catch (JAXBException e1) {
            e1.printStackTrace();
        }finally {
            //关闭输入流
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        ModelEvaluatorFactory modelEvaluatorFactory = ModelEvaluatorFactory.newInstance();
        Evaluator evaluator = modelEvaluatorFactory.newModelEvaluator(pmml);

        // Prepare the input data
        Map<FieldName, Double> inputData = new LinkedHashMap<>();
        inputData.put(new FieldName("feature_1"), (double) feature1);
        inputData.put(new FieldName("feature_2"), (double) feature2);
        inputData.put(new FieldName("feature_3"), (double) feature3);
        inputData.put(new FieldName("feature_4"), (double)feature4);
        inputData.put(new FieldName("feature_5"), (double)feature5);
        inputData.put(new FieldName("feature_6"), (double)feature6);

        // Evaluate the model
        Map<FieldName, ?> results = evaluator.evaluate(inputData);

        // Get the output
        String prediction = (String) results.get(new FieldName("output"));

        // Return the prediction as a string
        return "The predicted value is: " + prediction;
    }

}
