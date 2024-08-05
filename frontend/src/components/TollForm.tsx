import React from 'react';

interface TollFormProps {
    tollData: { licensePlate: string };
    setTollData: React.Dispatch<React.SetStateAction<{ licensePlate: string }>>;
    handleSubmit: (event: React.FormEvent<HTMLFormElement>) => void;
    vehicleInfo: any;
    error: string | null;
}

const TollForm: React.FC<TollFormProps> = ({ tollData, setTollData, handleSubmit, vehicleInfo, error }) => {
    // Function to determine if the error is related to an unknown vehicle license
    const isUnknownLicenseError = (errorMessage: string | null) => {
        return errorMessage?.includes("Unknown vehicle license");
    };

    return (
        <div className="max-w-2xl mx-auto bg-white p-6 rounded shadow-md mb-8">
            <h2 className="text-2xl font-semibold mb-4">Check Toll for Specific Vehicle</h2>
            <form onSubmit={handleSubmit}>
                <label className="block mb-4">
                    <span className="text-gray-700">License Plate Number:</span>
                    <input
                        type="text"
                        name="licensePlate"
                        value={tollData.licensePlate}
                        onChange={(e) => setTollData({ licensePlate: e.target.value })}
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
                <div className={`mt-4 p-4 border rounded ${vehicleInfo.isValid ? 'bg-green-100 border-green-200 text-green-600' : 'bg-red-100 border-red-200 text-red-600'}`}>
                    <h3 className="text-lg font-semibold">Vehicle Info:</h3>
                    <p>License Plate: {vehicleInfo.license}</p>
                    <p>Status: {vehicleInfo.isValid ? 'Valid' : 'Invalid'}</p>
                    {vehicleInfo.isValid && (
                        <p>Valid until: {vehicleInfo.validUntil}</p>
                    )}
                </div>
            )}

            {error && (
                <div className="mt-4 p-4 bg-red-100 border border-red-200 rounded text-red-600">
                    {isUnknownLicenseError(error) ? (
                        <p>Unknown vehicle license. Please check the license plate or register your car first.</p>
                    ) : (
                        <p>{error}</p>
                    )}
                </div>
            )}
        </div>
    );
};

export default TollForm;
