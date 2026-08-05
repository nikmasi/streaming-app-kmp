import { Component, ElementRef, ViewChild, AfterViewInit } from '@angular/core';
import Hls from 'hls.js';

@Component({
  selector: 'app-watch',
  templateUrl: './watch.html',
  styleUrl: './watch.css'
})
export class Watch implements AfterViewInit {

  @ViewChild('player')
  video!: ElementRef<HTMLVideoElement>;

  ngAfterViewInit() {

    const url =
      'http://localhost:8080/hls/6a738ff7b5a3223d08133517/master.m3u8';


    const video = this.video.nativeElement;


    if (Hls.isSupported()) {

      const hls = new Hls();

      hls.loadSource(url);
      hls.attachMedia(video);

    } 
    else if(video.canPlayType('application/vnd.apple.mpegurl')) {

      video.src = url;

    }
  }
}
