import React, { useState } from 'react';
import axios from 'axios';
import TollForm from './components/TollForm';
import PurchaseTollForm from './components/PurchaseTollForm';
import RegisterCarForm from './components/RegisterCarForm';
import VehicleInfo from './components/VehicleInfo';
import ErrorAlert from './components/ErrorAlert';

const Home: React.FC = () => {
    const [carData, setCarData] = useState({
        licensePlateNumber: '',
        manufactureDate: '',
        duration: 'MONTH' as 'WEEK' | 'MONTH' | 'YEAR'
    });
    const [tollData, setTollData] = useState({ licensePlate: '' });
    const [purchaseData, setPurchaseData] = useState({ licensePlate: '', duration: 'MONTH' as 'WEEK' | 'MONTH' | 'YEAR' });
    const [vehicleInfo, setVehicleInfo] = useState<any>(null);
    const [error, setError] = useState<string | null>(null);

    const apiKey = localStorage.getItem('apiKey');

    const handleRegisterCar = async (event: React.FormEvent<HTMLFormElement>) => {
        event.preventDefault();
        try {
            await axios.post('http://localhost:8080/cars', {
                licensePlateNumber: carData.licensePlateNumber,
                manufactureDate: carData.manufactureDate,
                duration: carData.duration
            }, {
                headers: {
                    'X-API-KEY': apiKey || ''
                }
            });
            alert('Car registered and toll paid successfully!');
        } catch (error: any) {
            setError(error.response?.data || 'An error occurred');
        }
    };

    const handlePayToll = async (event: React.FormEvent<HTMLFormElement>) => {
        event.preventDefault();
        try {
            const response = await axios.get(`http://localhost:8080/cars/${tollData.licensePlate}`, {
                headers: {
                    'X-API-KEY': apiKey || ''
                }
            });
            setVehicleInfo(response.data);
            setError(null); // Clear previous errors
        } catch (error: any) {
            setError(error.response?.data || 'An error occurred');
            setVehicleInfo(null); // Clear previous vehicle info
        }
    };


    const handlePurchaseToll = async (event: React.FormEvent<HTMLFormElement>) => {
        event.preventDefault();
        try {
            await axios.post(`http://localhost:8080/cars/${purchaseData.licensePlate}/toll?duration=${purchaseData.duration}`, {}, {
                headers: {
                    'X-API-KEY': apiKey || ''
                }
            });
            alert('Toll purchased successfully!');
            setPurchaseData({ licensePlate: '', duration: 'MONTH' }); // Reset purchase form
        } catch (error: any) {
            setError(error.response?.data || 'An error occurred');
        }
    };

    return (
        <div className="min-h-screen bg-gray-100 p-4">
            <TollForm
                tollData={tollData}
                setTollData={setTollData}
                handleSubmit={handlePayToll}
                vehicleInfo={vehicleInfo}
                error={error}
            />

            <PurchaseTollForm
                purchaseData={purchaseData}
                setPurchaseData={setPurchaseData}
                handleSubmit={handlePurchaseToll}
            />

            <RegisterCarForm
                carData={carData}
                setCarData={setCarData}
                handleSubmit={handleRegisterCar}
            />

            <ErrorAlert error={error} />
        </div>
    );
};

export default Home;
