/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fxmedia;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.util.Duration;


public class FXMLDocumentController implements Initializable 
{
    private MediaPlayer mediaPlayer;
    
    @FXML 
    private MediaView mediaView;
    
    @FXML
    private Slider slider;
    @FXML
    private Slider seekSlider;
    @FXML
    private Button replayButton;
    private String filePath;
    public int dem = 0;
    public int demSound = 0;
    @FXML
    private Button muteButton;
    @FXML
    private void handleButtonAction(ActionEvent event)
    {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Select a File (*.mp4)", "*.mp4");
        FileChooser.ExtensionFilter filter1 = new FileChooser.ExtensionFilter("Select a File (*.mp3)", "*.mp3");
        fileChooser.getExtensionFilters().add(filter);
        fileChooser.getExtensionFilters().add(filter1);
        File file = fileChooser.showOpenDialog(null);
        filePath = file.toURI().toString();
        
        if(filePath!=null)
        {
            
            Media media = new Media(filePath);
            mediaPlayer = new MediaPlayer(media);
            mediaView.setMediaPlayer(mediaPlayer);
            DoubleProperty width = mediaView.fitWidthProperty();
            DoubleProperty hight = mediaView.fitHeightProperty();
            
            width.bind(Bindings.selectDouble(mediaView.sceneProperty(), "width"));
            hight.bind(Bindings.selectDouble(mediaView.sceneProperty(), "hight"));
            
            slider.setValue(mediaPlayer.getVolume() * 100);
            slider.valueProperty().addListener(new InvalidationListener() {
                @Override
                public void invalidated(Observable o) {
                    mediaPlayer.setVolume(slider.getValue()/100);
                }
            });
            seekSlider.maxProperty().bind(Bindings.createDoubleBinding(
                            () -> mediaPlayer.getTotalDuration().toSeconds(),

                             mediaPlayer.totalDurationProperty()));
            mediaPlayer.currentTimeProperty().addListener(new ChangeListener<Duration>() {
                @Override
                public void changed(ObservableValue<? extends Duration> ov, Duration t, Duration t1) {
                    seekSlider.setValue(t1.toSeconds());
                }
            });
                seekSlider.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent t) {
                    mediaPlayer.seek(Duration.seconds(seekSlider.getValue()));
                }
            });
                
                replayButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    
            @Override
          
            public void handle(MouseEvent t) {
                
             if(t.getClickCount()==1)
             {
                 dem++;
                 
            }
             if(dem%2 !=0)
                 {
                     mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
                 if(mediaPlayer.getCurrentTime().toSeconds() == mediaPlayer.getTotalDuration().toSeconds()) 
                {
            
            mediaPlayer.seek(Duration.ZERO);
            mediaPlayer.play();
             }
                 }
             if(dem%2 == 0)
                {
                    mediaPlayer.setCycleCount(0);
                    if(mediaPlayer.getCurrentTime().toSeconds() == mediaPlayer.getTotalDuration().toSeconds()) 
                {
                    
                     mediaPlayer.stop();
                }
                }
            
            }
        });
            muteButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent t) 
                {
                    if(t.getClickCount()==1)
             {
                 demSound++;
                 
            }
                  if(demSound %2 !=0)
                  {
                      mediaPlayer.muteProperty().setValue(true);
                  }
                  else
                  {
                      mediaPlayer.muteProperty().setValue(false);
                  }
                }
            });
            mediaPlayer.play();
        }
        
     
    
    }
    
   

    @FXML
    private void resetSpeed(ActionEvent event)
    {
        mediaPlayer.setRate(1);
    }
     @FXML
      private void pauseVideo(ActionEvent event)
      {
          mediaPlayer.pause();
      }
      @FXML
      private void playVideo(ActionEvent event)
      {
          mediaPlayer.play();
          
      }
      @FXML
      private void stopVideo(ActionEvent event)
      {
          mediaPlayer.stop();
      }
      @FXML
      private void fastVideo(ActionEvent event)
      {
          mediaPlayer.setRate(1.25);
      }
      @FXML
      private void fasterVideo(ActionEvent event)
      {
          mediaPlayer.setRate(1.5);
      }
      @FXML
      private void slowVideo(ActionEvent event)
      {
          mediaPlayer.setRate(0.75);
      }
      @FXML
      private void slowerVideo(ActionEvent event)
      {
          mediaPlayer.setRate(0.5);
      }
      @FXML
      private void exitVideo(ActionEvent event)
      {
          System.exit(0);
      }
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        // TODO
    }    
    
}
   
    

