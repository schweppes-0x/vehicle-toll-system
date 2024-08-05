import React, { useState } from 'react';
import axios from 'axios';

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
            setError(error.response?.data?.message || 'An error occurred');
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
            setError(error.response?.data?.message || 'An error occurred');
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
            setError(error.response?.data?.message || 'An error occurred');
        }
    };

    const isValidToll = (validUntil: string | undefined) => {
        if (!validUntil || validUntil === '1970-01-01T00:00:00.000Z') {
            return false;
        }
        const now = new Date();
        const validUntilDate = new Date(validUntil);
        return validUntilDate > now;
    };

    return (
        <div className="min-h-screen bg-gray-100 p-4">
            {/* Pay Toll for Specific Vehicle */}
            <div className="max-w-2xl mx-auto bg-white p-6 rounded shadow-md mb-8">
                <h2 className="text-2xl font-semibold mb-4">Check Toll for Specific Vehicle</h2>
                <form onSubmit={handlePayToll}>
                    <label className="block mb-4">
                        <span className="text-gray-700">License Plate Number:</span>
                        <input
                            type="text"
                            name="licensePlate"
                            value={tollData.licensePlate}
                            onChange={(e) => setTollData({ ...tollData, licensePlate: e.target.value })}
                            className="mt-1 block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm"
                            required
                        />
                    </label>
                    <button
                        type="submit"
                        className="px-4 py-2 bg-blue-500 text-white font-semibold rounded"
                    >
                        Check Valid Toll
                    </button>
                </form>

                {vehicleInfo && (
                    <div className={`mt-4 p-4 border rounded ${isValidToll(vehicleInfo.validUntil) ? 'bg-green-100 border-green-200 text-green-600' : 'bg-red-100 border-red-200 text-red-600'}`}>
                        <h3 className="text-lg font-semibold">Vehicle Info:</h3>
                        <p>License Plate: {vehicleInfo.license}</p>
                        <p>Status: {isValidToll(vehicleInfo.validUntil) ? 'Valid' : 'Invalid'}</p>
                        {isValidToll(vehicleInfo.validUntil) && (
                            <p>Valid untill: {vehicleInfo.validUntil}</p>
                        )}
                    </div>
                )}

                {error && (
                    <div className="mt-4 p-4 bg-red-100 border border-red-200 rounded text-red-600">
                        {error}
                    </div>
                )}
            </div>

            {/* Purchase Toll */}
            <div className="max-w-2xl mx-auto bg-white p-6 rounded shadow-md">
                <h2 className="text-2xl font-semibold mb-4">Purchase Toll</h2>
                <form onSubmit={handlePurchaseToll}>
                    <label className="block mb-4">
                        <span className="text-gray-700">License Plate Number:</span>
                        <input
                            type="text"
                            name="licensePlate"
                            value={purchaseData.licensePlate}
                            onChange={(e) => setPurchaseData({ ...purchaseData, licensePlate: e.target.value })}
                            className="mt-1 block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm"
                            required
                        />
                    </label>
                    <label className="block mb-4">
                        <span className="text-gray-700">Duration:</span>
                        <select
                            name="duration"
                            value={purchaseData.duration}
                            onChange={(e) => setPurchaseData({ ...purchaseData, duration: e.target.value as 'WEEK' | 'MONTH' | 'YEAR' })}
                            className="mt-1 block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm"
                            required
                        >
                            <option value="WEEK">Week</option>
                            <option value="MONTH">Month</option>
                            <option value="YEAR">Year</option>
                        </select>
                    </label>
                    <button
                        type="submit"
                        className="px-4 py-2 bg-blue-500 text-white font-semibold rounded"
                    >
                        Purchase Toll
                    </button>
                </form>
            </div>

            {/* Register Car and Pay Toll */}
            <div className="max-w-2xl mx-auto bg-white p-6 rounded shadow-md mb-8">
                <h2 className="text-2xl font-semibold mb-4">Register a Car and Pay Toll</h2>
                <form onSubmit={handleRegisterCar}>
                    <label className="block mb-4">
                        <span className="text-gray-700">License Plate Number:</span>
                        <input
                            type="text"
                            name="licensePlateNumber"
                            value={carData.licensePlateNumber}
                            onChange={(e) => setCarData({ ...carData, licensePlateNumber: e.target.value })}
                            className="mt-1 block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm"
                            required
                        />
                    </label>
                    <label className="block mb-4">
                        <span className="text-gray-700">Manufacture Date:</span>
                        <input
                            type="date"
                            name="manufactureDate"
                            value={carData.manufactureDate}
                            onChange={(e) => setCarData({ ...carData, manufactureDate: e.target.value })}
                            className="mt-1 block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm"
                            required
                        />
                    </label>
                    <label className="block mb-4">
                        <span className="text-gray-700">Duration:</span>
                        <select
                            name="duration"
                            value={carData.duration}
                            onChange={(e) => setCarData({ ...carData, duration: e.target.value as 'WEEK' | 'MONTH' | 'YEAR' })}
                            className="mt-1 block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm"
                            required
                        >
                            <option value="WEEK">Week</option>
                            <option value="MONTH">Month</option>
                            <option value="YEAR">Year</option>
                        </select>
                    </label>
                    <button
                        type="submit"
                        className="px-4 py-2 bg-blue-500 text-white font-semibold rounded"
                    >
                        Register and Pay Toll
                    </button>
                </form>
            </div>


        </div>
    );
};

export default Home;
