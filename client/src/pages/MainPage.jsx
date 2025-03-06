import GoogleMap from "../components/GoogleMap.jsx";
import useGeolocation from "../hook/useGeolocation.jsx";
import LoadPanel from "../components/LoadPanel.jsx";

export default function MainPage() {
    const location = useGeolocation();
    const markers = [
        { name: '다빈이네', lat: 37.4717946, lng: 126.9183643 },
        { name: '경민이네', lat: 37.4728000, lng: 126.9183645 },
        { name: '짐박스', lat: 37.4730000, lng: 126.9183640 },
    ];

    return (
        <>
            <div className={'flex justify-center mt-5'}>
                <h1 className={'text-2xl font-bold'}>Welcome Todo List!</h1>
            </div>
            {location.loaded &&
                <GoogleMap width={'100%'}
                    height={'1000px'}
                    lat={location.coordinates.lat}
                    lng={location.coordinates.lng}
                    zoom={18}
                    onChange={(data) => {console.log("####3 ", data)}}
                    markers={markers} />
            }
            {!location.loaded &&
                <>
                    <div className={'flex justify-center mt-3'}>위치 정보 이용을 동의해야만 이용 가능합니다.</div>
                    <LoadPanel isActive={true}/>
                </>
            }
        </>
    )
}