import { Map, InfoWindow, AdvancedMarker, Pin } from "@vis.gl/react-google-maps";
import PinImage from '../assets/pin.jpeg';
import {useState} from "react";



export default function GoogleMap({ width, height, lat, lng, zoom, markers, onChange }) {
    const [curMarker, setCurMaker] = useState(null);

    const onBoundsChanged = (data) => {
        onChange(data.detail.bounds);
    }

    return (
        <Map
            style={{ width, height }}
            defaultCenter={{ lat, lng }}
            defaultZoom={zoom}
            gestureHandling={'greedy'}
            disableDefaultUI
            mapId={'7c6badb6cec07ad8'}
            onBoundsChanged={onBoundsChanged}
        >
            {curMarker &&
                <InfoWindow position={{ lat: curMarker.lat, lng: curMarker.lng }}
                            onClose={() => setCurMaker(null)}>
                    {curMarker.name}
                </InfoWindow>
            }

            {markers.map((marker, idx) => {
                return (
                    <AdvancedMarker position={{lat: marker.lat, lng: marker.lng}}
                        onClick={() => setCurMaker(marker)}>
                        <div key={idx} className={'flex items-center'}>
                            {marker.name} <img src={PinImage} width={30} height={30}/>
                        </div>
                    </AdvancedMarker>
                )
            })}
        </Map>
    )
}